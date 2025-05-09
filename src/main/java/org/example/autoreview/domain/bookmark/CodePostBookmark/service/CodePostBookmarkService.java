package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkListResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmarkRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = false)
public class CodePostBookmarkService {

    private final CodePostBookmarkRepository codePostBookmarkRepository;
    private final MemberCommand memberCommand;

    public Long saveOrUpdate(CodePostBookmarkSaveRequestDto requestDto, String email) {
        Member member = memberCommand.findByEmail(email);
        Optional<CodePostBookmark> existingBookmark =
                codePostBookmarkRepository.findById(requestDto.codePostId(), member.getId());

        return existingBookmark
                .map(CodePostBookmark::update)
                .orElseGet(() -> codePostBookmarkRepository.save(requestDto.toEntity(member)).getId());
    }

    @Transactional(readOnly = true)
    public CodePostBookmarkListResponseDto findAllByMemberId(String email, Pageable pageable) {
        Long memberId = memberCommand.findByEmail(email).getId();
        Page<CodePostBookmarkResponseDto> pageDto = codePostBookmarkRepository.findAllByMemberId(memberId,pageable);

        return new CodePostBookmarkListResponseDto(pageDto.getContent(),pageDto.getTotalPages());
    }

    public void deleteExpiredSoftDeletedBookmarks() {
        codePostBookmarkRepository.deleteExpiredSoftDeletedBookmarks();
    }
}
