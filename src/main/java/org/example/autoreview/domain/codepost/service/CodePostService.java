package org.example.autoreview.domain.codepost.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.bookmark.CodePostBookmark.service.CodePostBookmarkCommand;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostListResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.Language;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.domain.notification.entity.Notification;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.domain.notification.service.NotificationCommand;
import org.example.autoreview.domain.review.dto.response.ReviewResponseDto;
import org.example.autoreview.domain.review.entity.Review;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodePostService {

    private final CodePostCommand codePostCommand;
    private final MemberCommand memberCommand;
    private final CodePostBookmarkCommand codePostBookmarkCommand;
    private final NotificationCommand notificationCommand;

    /**
     * 코드 포스트 저장 메서드이다.
     * 복습일을 설정했을 경우에 notification Entity 를 저장한다.
     */
    public Long save(CodePostSaveRequestDto requestDto, String email) {
        Member member = memberCommand.findByEmail(email);
        CodePost codePost = codePostCommand.save(requestDto.toEntity(member));

        if (requestDto.reviewDay() != null) {
            Notification notification = Notification.builder()
                    .title("ORI 복습 알림")
                    .content(codePost.getTitle())
                    .status(NotificationStatus.PENDING)
                    .executeTime(codePost.getReviewDay())
                    .member(member)
                    .codePostId(codePost.getId())
                    .build();
            notificationCommand.save(notification);
        }
        return codePost.getId();
    }

    /**
     * 키워드에 맞는 제목을 가진 코드 포스트를 조회하는 검색 메서드이다.
     * 키워드 뒤에 와일드 카드를 붙혀서 연관된 결과도 조회한다.
     */
    public CodePostListResponseDto search(String keyword, int page, int size, String direction, String sortBy, String language) {
        keywordValidator(keyword);
        String wildcardKeyword = keyword + "*";
        Language lang = Language.of(language);

        if (sortBy.equals("commentCount")) {
            Pageable pageable = PageRequest.of(page, size);
            return searchSortByCommentCount(wildcardKeyword, pageable, direction, lang);
        }

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return searchSorted(wildcardKeyword, pageable, lang);
    }

    private CodePostListResponseDto searchSortByCommentCount(String keyword, Pageable pageable, String direction, Language language) {
        Page<CodePostThumbnailResponseDto> page;
        if (direction.equals("desc")) {
            page = codePostCommand.searchSortByCommentCountDesc(keyword, pageable, language);
        } else {
            page = codePostCommand.searchSortByCommentCountAsc(keyword, pageable, language);
        }
        return new CodePostListResponseDto(page.getContent(), page.getTotalPages());
    }

    private CodePostListResponseDto searchSorted(String keyword, Pageable pageable, Language language) {
        Page<CodePost> codePostPage = codePostCommand.search(keyword, pageable);

        List<CodePostThumbnailResponseDto> content = codePostPage
                .stream()
                .filter(post -> post.isPublic() && (language == Language.ALL || post.getLanguage() == language))
                .map(post -> {
                    Member member = memberCommand.findById(post.getWriterId());
                    return new CodePostThumbnailResponseDto(post, member);
                })
                .toList();

        return new CodePostListResponseDto(content, codePostPage.getTotalPages());
    }

    /**
     * code_post_id를 통해 단일 조회하는 메서드이다.
     * 1. member_id를 통해 해당 글이 공개인지 비공개인지 결정한다.
     * 2. 반환값에서는 포스트, 작성자, 리뷰, 북마크 여부를 포함한다.
     */
    @Transactional(readOnly = true)
    public CodePostResponseDto findById(Long id, String email) {
        Member member = memberCommand.findByEmail(email);
        CodePost codePost = codePostCommand.findByIdIsPublic(id, member.getId());
        Member writer = memberCommand.findById(codePost.getWriterId());
        Optional<CodePostBookmark> codePostBookmark = codePostBookmarkCommand.findByCodePostBookmark(email, codePost.getId());

        boolean isBookmarked = false;
        if (codePostBookmark.isPresent()) {
            isBookmarked = !codePostBookmark.get().isDeleted();
        }

        List<Review> reviews = codePost.getReviewList();
        List<ReviewResponseDto> dtoList = reviews.stream()
                .map(ReviewResponseDto::new)
                .toList();

        return new CodePostResponseDto(codePost, dtoList, writer, isBookmarked);
    }

    /**
     * 페이지네이션 정보와 필터할 키워드를 통해 포스트를 페이징 조회하는 메서드이다.
     * 1.[direction: 오(내)림차순] / [sortBy: 정렬 기준] / [language: 프로그래밍 언어]
     * 2. 정렬 기준이 commentCount 일 때에는 CodePost Entity 에 존재하지 않는 컬럼이기 때문에 따로 페이징 처리한다.
     */
    public CodePostListResponseDto findPostByPage(int page, int size, String direction, String sortBy, String language){
        Language lang = Language.of(language);
        if(sortBy.equals("commentCount")) {
            Pageable pageable = PageRequest.of(page, size);
            return findByPageSortByCommentCount(pageable,direction,lang);
        }
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,sortBy));
        return findByPage(pageable,lang);
    }

    private CodePostListResponseDto findByPageSortByCommentCount(Pageable pageable, String direction, Language language) {
        if (direction.equals("desc")) {
            Page<CodePostThumbnailResponseDto> page = codePostCommand.findByPageSortByCommentCountDesc(pageable, language);
            return new CodePostListResponseDto(page.getContent(), page.getTotalPages());
        }
        Page<CodePostThumbnailResponseDto> page = codePostCommand.findByPageSortByCommentCountAsc(pageable, language);
        return new CodePostListResponseDto(page.getContent(), page.getTotalPages());
    }

    private CodePostListResponseDto findByPage(Pageable pageable, Language language) {
        Page<CodePostThumbnailResponseDto> page = codePostCommand.findByPage(pageable, language);
        return new CodePostListResponseDto(page.getContent(), page.getTotalPages());
    }

    /**
     * 사용자가 작성한 모든 코드 포스트를 조회하는 메서드이다.
     */
    public CodePostListResponseDto findByMemberId(String email, int page, int size, String direction, String sortBy, String language) {
        Member member = memberCommand.findByEmail(email);
        Language lang = Language.of(language);

        Pageable pageable;
        if (sortBy.equals("commentCount")) {
            pageable = PageRequest.of(page, size);
            return findByMemberIdSortByCommentCount(pageable, direction, member.getId(), lang);
        }

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<CodePost> codePostPage = codePostCommand.findByMemberId(member.getId(), pageable);

        List<CodePostThumbnailResponseDto> content = codePostPage
                .stream()
                .filter(post -> post.isPublic() && (lang == Language.ALL || post.getLanguage() == lang))
                .map(post -> new CodePostThumbnailResponseDto(post, member))
                .toList();

        return new CodePostListResponseDto(content, codePostPage.getTotalPages());
    }

    private CodePostListResponseDto findByMemberIdSortByCommentCount(Pageable pageable, String direction, Long memberId, Language language) {
        Page<CodePostThumbnailResponseDto> page;

        if (direction.equalsIgnoreCase("desc")) {
            page = codePostCommand.findByMemberIdSortByCommentCountDesc(pageable, memberId, language);
        } else {
            page = codePostCommand.findByMemberIdSortByCommentCountAsc(pageable, memberId, language);
        }

        return new CodePostListResponseDto(page.getContent(), page.getTotalPages());
    }


    /**
     * 사용자가 작성한 포스트들 중 키워드에 맞는 포스트들을 조회하는 메서드이다.
     */
    public CodePostListResponseDto mySearch(String keyword, String email, int page, int size, String direction, String sortBy, String language) {
        Member member = memberCommand.findByEmail(email);
        keywordValidator(keyword);
        Language lang = Language.of(language);

        if (sortBy.equals("commentCount")) {
            Pageable pageable = PageRequest.of(page, size);
            return mySearchSortByCommentCount(keyword, member, pageable, direction, lang);
        }

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return mySearchSorted(keyword, member, pageable, lang);
    }

    private CodePostListResponseDto mySearchSortByCommentCount(String keyword, Member member, Pageable pageable, String direction, Language language) {
        Page<CodePostThumbnailResponseDto> page;
        if (direction.equals("desc")) {
            page = codePostCommand.mySearchSortByCommentCountDesc(keyword, pageable, member.getId(), language);
        } else {
            page = codePostCommand.mySearchSortByCommentCountAsc(keyword, pageable, member.getId(), language);
        }
        return new CodePostListResponseDto(page.getContent(), page.getTotalPages());
    }

    private CodePostListResponseDto mySearchSorted(String keyword, Member member, Pageable pageable, Language language) {
        Page<CodePost> codePostPage = codePostCommand.mySearch(keyword, pageable, member.getId());

        List<CodePostThumbnailResponseDto> content = codePostPage
                .stream()
                .filter(post -> post.isPublic() && (language == Language.ALL || post.getLanguage() == language))
                .map(post -> new CodePostThumbnailResponseDto(post, member))
                .toList();

        return new CodePostListResponseDto(content, codePostPage.getTotalPages());
    }

    private static void keywordValidator(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PARAMETER.getMessage());
        }
    }

    /**
     * 사용자 유효성 검사 후 포스트를 업데이트하는 메서드이다.
     * 1. 복습일 설정을 on 에서 off 로 변경할 경우 삭제
     * 2. 복습일 설정을 on 에서 on 으로 변경할 경우 업데이트
     * 3. 복습일 설정을 off 에서 on 으로 변경할 경우 저장
     */
    public Long update(CodePostUpdateRequestDto requestDto, String email) {
        CodePost codePost = codePostCommand.findById(requestDto.getId());
        memberValidator(email, codePost);
        Member member = memberCommand.findByEmail(email);
        codePostCommand.update(codePost.getId(), requestDto);

        boolean notificationExists = notificationCommand.existsByCodePostId(requestDto.getId());

        if (notificationExists) {
            Notification notification = notificationCommand.findByCodePostId(codePost.getId());
            if (requestDto.getReviewDay() == null) {
                notificationCommand.delete(notification);
            } else {
                notificationCommand.update(notification, codePost, NotificationStatus.PENDING);
            }
        } else if (requestDto.getReviewDay() != null){
            notificationSave(member, requestDto);
        }
        return codePost.getId();
    }

    private void notificationSave(Member member, CodePostUpdateRequestDto requestDto) {
        Notification notification = Notification.builder()
                .title("ORI 복습 알림")
                .content(requestDto.getTitle())
                .status(NotificationStatus.PENDING)
                .executeTime(requestDto.getReviewDay())
                .member(member)
                .codePostId(requestDto.getId())
                .build();
        notificationCommand.save(notification);
    }

    /**
     * 사용자 유효성 검사 후 포스트를 삭제하는 메서드이다.
     * 1. 해당 포스트에 알림이 존재할 경우 삭제한다.
     */
    public Long delete(Long id, String email) {
        if (notificationCommand.existsByCodePostId(id)) {
            Notification notification = notificationCommand.findByCodePostId(id);
            notificationCommand.delete(notification);
        }
        CodePost codePost = codePostCommand.findById(id);
        memberValidator(email, codePost);
        codePostCommand.delete(codePost);
        return id;
    }

    private void memberValidator(String loginMemberEmail, CodePost codePost) {
        Member writer = memberCommand.findById(codePost.getWriterId());
        if (!writer.getEmail().equals(loginMemberEmail)) {
            throw new BadRequestException(ErrorCode.UNMATCHED_EMAIL);
        }
    }

}
