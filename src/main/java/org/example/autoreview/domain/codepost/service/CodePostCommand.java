package org.example.autoreview.domain.codepost.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.CodePostRepository;
import org.example.autoreview.domain.codepost.entity.Language;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CodePostCommand {

    private final CodePostRepository codePostRepository;

    @Transactional
    public CodePost save(CodePost codePost) {
        return codePostRepository.save(codePost);
    }

    @Transactional(readOnly = true)
    public CodePost findById(Long id) {
        return codePostRepository.findById(id).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_POST)
        );
    }

    @Transactional(readOnly = true)
    public CodePost findByIdIsPublic(Long codePostId, Long memberId) {
        return codePostRepository.findByIdIsPublic(codePostId, memberId).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_POST)
        );
    }

    @Transactional(readOnly = true)
    public Page<CodePost> search(String wildcardKeyword, Pageable pageable) {
        return codePostRepository.search(wildcardKeyword, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CodePost> findByMemberId(Long memberId, Pageable pageable) {
        return codePostRepository.findByMemberId(memberId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CodePostThumbnailResponseDto> findByMemberIdSortByCommentCountDesc(Pageable pageable, Long memberId, Language language) {
        return codePostRepository.findByMemberIdSortByCommentCountDesc(pageable, memberId, language);
    }

    @Transactional(readOnly = true)
    public Page<CodePostThumbnailResponseDto> findByMemberIdSortByCommentCountAsc(Pageable pageable, Long memberId, Language language) {
        return codePostRepository.findByMemberIdSortByCommentCountAsc(pageable, memberId, language);
    }

    @Transactional(readOnly = true)
    public Page<CodePost> mySearch(String keyword, Pageable pageable, Long memberId) {
        return codePostRepository.mySearch(keyword, pageable, memberId);
    }

    @Transactional(readOnly = true)
    public Page<CodePostThumbnailResponseDto> mySearchSortByCommentCountDesc(String keyword, Pageable pageable, Long memberId, Language language) {
        return codePostRepository.mySearchSortByCommentCountDesc(keyword, pageable, memberId, language);
    }

    @Transactional(readOnly = true)
    public Page<CodePostThumbnailResponseDto> mySearchSortByCommentCountAsc(String keyword, Pageable pageable, Long memberId, Language language) {
        return codePostRepository.mySearchSortByCommentCountAsc(keyword, pageable, memberId, language);
    }

    @Transactional(readOnly = true)
    public Page<CodePostThumbnailResponseDto> findByPage(Pageable pageable, Language language) {
        return codePostRepository.findByPage(pageable, language);
    }

    @Transactional(readOnly = true)
    public Page<CodePostThumbnailResponseDto> findByPageSortByCommentCountDesc(Pageable pageable, Language language) {
        return codePostRepository.findByPageSortByCommentCountDesc(pageable, language);
    }

    @Transactional(readOnly = true)
    public Page<CodePostThumbnailResponseDto> findByPageSortByCommentCountAsc(Pageable pageable, Language language) {
        return codePostRepository.findByPageSortByCommentCountAsc(pageable, language);
    }

    @Transactional(readOnly = true)
    public Page<CodePostThumbnailResponseDto> searchSortByCommentCountDesc(String keyword, Pageable pageable, Language language) {
        return codePostRepository.searchSortByCommentCountDesc(keyword, pageable, language);
    }

    @Transactional(readOnly = true)
    public Page<CodePostThumbnailResponseDto> searchSortByCommentCountAsc(String keyword, Pageable pageable, Language language) {
        return codePostRepository.searchSortByCommentCountAsc(keyword, pageable, language);
    }

    @Transactional
    public void update(Long codePostId, CodePostUpdateRequestDto requestDto) {
        CodePost codePost = codePostRepository.findById(codePostId).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_POST)
        );
        codePost.update(requestDto);
    }

    @Transactional
    public void delete(CodePost codePost) {
        codePostRepository.delete(codePost);
    }
}
