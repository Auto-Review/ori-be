package org.example.autoreview.domain.codepost.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostListResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.CodePostRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.domain.review.dto.response.ReviewResponseDto;
import org.example.autoreview.domain.review.entity.Review;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CodePostService {

    private final CodePostRepository codePostRepository;
    private final MemberCommand memberCommand;

    @Transactional
    public CodePost save(CodePostSaveRequestDto requestDto, Member member) {
        CodePost codePost = requestDto.toEntity(member);
        return codePostRepository.save(codePost);
    }

    public CodePost findEntityById(Long id) {
        return codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
    }

    public CodePostResponseDto findById(Long id) {
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
        Member member = memberCommand.findById(codePost.getWriterId());
        List<Review> reviews = codePost.getReviewList();
        List<ReviewResponseDto> dtoList = reviews.stream()
                .map(ReviewResponseDto::new)
                .toList();

        return new CodePostResponseDto(codePost, dtoList, member);
    }

    public CodePostListResponseDto search(String keyword, Pageable pageable) {
        keywordValidator(keyword);
        String wildcardKeyword = keyword + "*";
        Page<CodePostThumbnailResponseDto> codePostPage = codePostRepository.search(wildcardKeyword, pageable)
                .map(post -> {
                    Member member = memberCommand.findById(post.getWriterId());
                    return new CodePostThumbnailResponseDto(post, member);
                });

        return new CodePostListResponseDto(codePostPage.getContent(), codePostPage.getTotalPages());
    }

    public CodePostListResponseDto findByMemberId(Pageable pageable, Member member) {
        Page<CodePost> codePostPage = codePostRepository.findByMemberId(member.getId(), pageable);
        return new CodePostListResponseDto(convertListDto(codePostPage,member), codePostPage.getTotalPages());
    }

    public CodePostListResponseDto mySearch(String keyword, Pageable pageable, Member member) {
        keywordValidator(keyword);
        Page<CodePost> codePostPage = codePostRepository.mySearch(keyword, pageable, member.getId());
        return new CodePostListResponseDto(convertListDto(codePostPage,member), codePostPage.getTotalPages());
    }

    private static void keywordValidator(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PARAMETER.getMessage());
        }
    }

    private List<CodePostThumbnailResponseDto> convertListDto(Page<CodePost> page, Member member) {
        return page.stream()
                .map(post -> getCodePostThumbnailResponseDto(post,member))
                .collect(Collectors.toList());
    }

    private CodePostThumbnailResponseDto getCodePostThumbnailResponseDto(CodePost codePost, Member member) {
        return new CodePostThumbnailResponseDto(codePost, member);
    }

    public CodePostListResponseDto findByPage(Pageable pageable) {
        Page<CodePostThumbnailResponseDto> page = codePostRepository.findByPage(pageable);

        return new CodePostListResponseDto(page.getContent(), page.getTotalPages());
    }

    @Transactional
    public CodePost update(CodePostUpdateRequestDto requestDto, String email) {
        CodePost codePost = codePostRepository.findById(requestDto.getId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
        memberValidator(email, codePost);
        codePost.update(requestDto);
        return codePost;
    }

    @Transactional
    public Long delete(Long id, String email) {
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
        memberValidator(email, codePost);
        codePostRepository.delete(codePost);
        return id;
    }

    private void memberValidator(String loginMemberEmail, CodePost codePost) {
        Member writer = memberCommand.findById(codePost.getWriterId());
        if (!writer.getEmail().equals(loginMemberEmail)) {
            throw new BadRequestException(ErrorCode.UNMATCHED_EMAIL);
        }
    }

}
