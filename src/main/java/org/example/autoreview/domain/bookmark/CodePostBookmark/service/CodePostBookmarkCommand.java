package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmarkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CodePostBookmarkCommand {

    private final CodePostBookmarkRepository codePostBookmarkRepository;

    @Transactional
    public Optional<CodePostBookmark> saveOrUpdate(CodePostBookmarkSaveRequestDto requestDto, String email) {
        codePostBookmarkRepository.upsert(email, requestDto.codePostId());
        return codePostBookmarkRepository.findById(email, requestDto.codePostId());
    }

    @Transactional
    public void deleteExpiredSoftDeletedBookmarks() {
        codePostBookmarkRepository.deleteExpiredSoftDeletedBookmarks();
    }

    @Transactional(readOnly = true)
    public Optional<CodePostBookmark> findByCodePostBookmark(String email, Long codePostId) {
        return codePostBookmarkRepository.findById(email,codePostId);
    }

    @Transactional(readOnly = true)
    public Page<CodePostBookmarkResponseDto> findAllByEmail(String email, Pageable pageable) {
        return codePostBookmarkRepository.findAllByEmail(email, pageable);
    }
}
