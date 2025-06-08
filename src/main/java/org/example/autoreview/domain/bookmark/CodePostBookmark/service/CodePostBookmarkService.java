package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkListResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkResponseDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodePostBookmarkService {

    private final CodePostBookmarkCommand codePostBookmarkCommand;
    private final MemberCommand memberCommand;

    /**
     * 북마크가 없을 시 생성, 있으면 상태 변경하는 메서드이다.
     * 1. DB에 code_post_id, member_id로 UNIQUE 제약 조건 설정
     * 2. UNIQUE 제약 조건 위반 시 catch를 통해 update() 로직 실행
     */
    public Long saveOrUpdate(CodePostBookmarkSaveRequestDto requestDto, String email) {
        Member member = memberCommand.findByEmail(email);

        try {
            return codePostBookmarkCommand.trySave(requestDto, member);
        } catch (DataIntegrityViolationException e) {
            return codePostBookmarkCommand.fallbackToUpdate(requestDto.codePostId(), member.getId());
        }
    }

    /**
     * 회원 아이디를 통해 북마크한 포스트를 조회하는 메서드이다.
     */
    public CodePostBookmarkListResponseDto findAllByMemberId(String email, Pageable pageable) {
        Long memberId = memberCommand.findByEmail(email).getId();
        Page<CodePostBookmarkResponseDto> pageDto = codePostBookmarkCommand.findAllByMemberId(memberId,pageable);

        return new CodePostBookmarkListResponseDto(pageDto.getContent(),pageDto.getTotalPages());
    }

    /**
     * 북마크 Table에서 soft delete 되어있는 컬럼을 전부 삭제하는 메서드이다.
     * Scheduler에서 쓰일 예정
     */
    public void deleteExpiredSoftDeletedBookmarks() {
        codePostBookmarkCommand.deleteExpiredSoftDeletedBookmarks();
    }
}
