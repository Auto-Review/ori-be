package org.example.autoreview.domain.tilpost.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.domain.tilpost.dto.request.TILPostSaveRequestDto;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;
import org.example.autoreview.domain.tilpost.dto.response.TILCursorResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostThumbnailResponseDto;
import org.example.autoreview.domain.tilpost.entity.TILPost;
import org.example.autoreview.domain.tilpost.entity.TILPostRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TILPostService {

    private final TILPostRepository tilPostRepository;
    private final MemberCommand memberCommand;

    @Transactional
    public Long save(TILPostSaveRequestDto requestDto, Member member) {
        TILPost tilPost = requestDto.toEntity(member);
        return tilPostRepository.save(tilPost).getId();
    }

    public TILPageResponseDto findByMember(Member member, Pageable pageable){
        Page<TILPost> posts = tilPostRepository.findTILPostsByWriterIdOrderByIdDesc(member.getId(), pageable);
        return new TILPageResponseDto(convertToListDto(posts,member), posts.getTotalPages());
    }

    public TILPageResponseDto findByMemberTitleContains(Member member, String keyword, Pageable pageable){
        Page<TILPost> posts = tilPostRepository.findTILPostsByWriterIdAndTitleContainingOrderByIdDesc(member.getId(), keyword, pageable);
        return new TILPageResponseDto(convertToListDto(posts,member), posts.getTotalPages());
    }

    public TILPageResponseDto findAllByPage(Pageable pageable){
        Page<TILPostThumbnailResponseDto> posts = tilPostRepository.findDtoAll(pageable);
        return new TILPageResponseDto(posts.getContent(), posts.getTotalPages());
    }

    public TILPageResponseDto findByTitleContains(String keyword, Pageable pageable){
        Page<TILPostThumbnailResponseDto> posts = tilPostRepository.findByTitleContaining(keyword, pageable);
        return new TILPageResponseDto(posts.getContent(), posts.getTotalPages());
    }

    public TILPageResponseDto findByIdList(List<Long> idList, Pageable pageable){
        Page<TILPostThumbnailResponseDto> posts = tilPostRepository.findByIdInOrderByIdDesc(idList, pageable);
        return new TILPageResponseDto(posts.getContent(), posts.getTotalPages());
    }

    private List<TILPostThumbnailResponseDto> convertToListDto(Page<TILPost> entity,Member member){
        return entity.stream()
                .map(post -> getTILPostThumbnailResponseDto(post,member))
                .collect(Collectors.toList());
    }

    private TILPostThumbnailResponseDto getTILPostThumbnailResponseDto(TILPost entity, Member member){
        return new TILPostThumbnailResponseDto(entity,member);
    }

    public TILPostResponseDto findById(Long id){
        TILPost tilPost = tilPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
        Member member = memberCommand.findById(tilPost.getWriterId());
        return new TILPostResponseDto(tilPost, member);
    }

    public TILPost findEntityById(Long id){
        return tilPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
    }

    @Transactional
    public Long update(TILPostUpdateRequestDto requestDto, String email) {
        Long id = requestDto.getId();

        TILPost tilPost = tilPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_POST));

        memberValidator(email, tilPost);
        tilPost.update(requestDto);

        return id;
    }

    @Transactional
    public Long delete(Long id, String email){
        TILPost tilPost = tilPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_POST));

        memberValidator(email, tilPost);
        tilPostRepository.delete(tilPost);

        return id;
    }

    private void memberValidator(String loginMemberEmail, TILPost tilPost) {
        Member member = memberCommand.findById(tilPost.getWriterId());
        if (!member.getEmail().equals(loginMemberEmail)) {
            throw new BadRequestException(ErrorCode.UNMATCHED_EMAIL);
        }
    }

    public TILCursorResponseDto findAllByIdCursorBased(Long cursorId, int pageSize, Member member){
        Pageable pageable = PageRequest.of(0, pageSize + 1);

        List<TILPost> posts = findAllByCursorIdCheckExistsCursor(cursorId, pageable);

        boolean hasNext = hasNext(posts.size(), pageSize);

        return new TILCursorResponseDto (
                toSubListIfHasNext(hasNext, pageSize, posts).stream()
                        .map(post -> getTILPostThumbnailResponseDto(post,member))
                        .collect(Collectors.toList()),
                cursorId, pageSize);
    }

    private List<TILPost> findAllByCursorIdCheckExistsCursor(Long cursorId, Pageable pageable){
        return cursorId == null ? tilPostRepository.findAllByOrderByIdDesc(pageable)
                : tilPostRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
    }

    private boolean hasNext(int postsSize, int pageSize){
        if(postsSize == 0){
            throw new NotFoundException(ErrorCode.NOT_FOUND_POST);
        }

        return postsSize > pageSize;
    }

    private List<TILPost> toSubListIfHasNext(boolean hasNext, int pageSize, List<TILPost> posts){
        return hasNext ? posts.subList(0, pageSize) : posts;
    }
}
