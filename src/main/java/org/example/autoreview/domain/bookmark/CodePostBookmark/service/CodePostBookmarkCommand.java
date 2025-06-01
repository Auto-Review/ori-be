package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmarkRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CodePostBookmarkCommand {

    private final CodePostBookmarkRepository codePostBookmarkRepository;

    @Transactional(readOnly = true)
    public Optional<CodePostBookmark> findByCodePostBookmark(Long memberId, Long codePostId) {
        return codePostBookmarkRepository.findById(memberId,codePostId);
    }
}
