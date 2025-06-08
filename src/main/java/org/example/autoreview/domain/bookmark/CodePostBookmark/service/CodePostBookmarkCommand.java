package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmarkRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CodePostBookmarkCommand {

    private final CodePostBookmarkRepository codePostBookmarkRepository;

    @Transactional
    public Long trySave(CodePostBookmarkSaveRequestDto requestDto, Member member) {
        return codePostBookmarkRepository.save(requestDto.toEntity(member)).getId();
    }

    @Transactional
    public Long fallbackToUpdate(Long codePostId, Long memberId) {
        return codePostBookmarkRepository.findById(memberId, codePostId)
                .map(CodePostBookmark::update)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.NOT_FOUND_BOOKMARK));
    }

    @Transactional
    public void deleteExpiredSoftDeletedBookmarks() {
        codePostBookmarkRepository.deleteExpiredSoftDeletedBookmarks();
    }

    @Transactional(readOnly = true)
    public Optional<CodePostBookmark> findByCodePostBookmark(Long memberId, Long codePostId) {
        return codePostBookmarkRepository.findById(memberId,codePostId);
    }

    @Transactional(readOnly = true)
    public Page<CodePostBookmarkResponseDto> findAllByMemberId(Long memberId, Pageable pageable) {
        return codePostBookmarkRepository.findAllByMemberId(memberId, pageable);
    }
}
