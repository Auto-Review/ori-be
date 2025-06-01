package org.example.autoreview.domain.bookmark;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmarkRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookmarkHelper {

    private final CodePostBookmarkRepository codePostBookmarkRepository;

    @Transactional
    public Long trySave(CodePostBookmarkSaveRequestDto requestDto, Member member) {
        return codePostBookmarkRepository.save(requestDto.toEntity(member)).getId();
    }

    @Transactional
    public Long fallbackToUpdate(Long codePostId, Long memberId) {
        return codePostBookmarkRepository.findById(codePostId, memberId)
                .map(CodePostBookmark::update)
                .orElseThrow(() -> new IllegalStateException("Bookmark should exist but was not found"));
    }
}
