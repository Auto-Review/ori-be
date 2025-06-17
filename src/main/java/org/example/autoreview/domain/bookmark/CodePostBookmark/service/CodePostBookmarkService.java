package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkListResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkResponseDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
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
     * MySQL 에서 지원하는 Upsert 기능을 통해 유니크 키가 중복될 경우 update 실행
     */
    public Long saveOrUpdate(CodePostBookmarkSaveRequestDto requestDto, String email) {
        Member member = memberCommand.findByEmail(email);
        return codePostBookmarkCommand.saveOrUpdate(requestDto, member).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_BOOKMARK)
        ).getId();
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
