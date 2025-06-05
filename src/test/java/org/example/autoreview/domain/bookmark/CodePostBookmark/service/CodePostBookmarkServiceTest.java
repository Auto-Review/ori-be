package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.Role;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CodePostBookmarkServiceTest {

    @Mock
    private CodePostBookmarkCommand codePostBookmarkCommand;

    @Mock
    private MemberCommand memberCommand;

    @InjectMocks
    private CodePostBookmarkService codePostBookmarkService;

    private Member mockMember;

    @BeforeEach
    void setUp() {
        mockMember = Member.builder()
                .nickname("tester")
                .role(Role.USER)
                .email("test@example.com")
                .build();
    }

    @Test
    void saveOrUpdate_성공적으로_북마크를_저장한다() {
        // given
        CodePostBookmarkSaveRequestDto requestDto = new CodePostBookmarkSaveRequestDto(123L);
        when(memberCommand.findByEmail("test@example.com")).thenReturn(mockMember);
        when(codePostBookmarkCommand.trySave(requestDto, mockMember)).thenReturn(1L);

        // when
        Long result = codePostBookmarkService.saveOrUpdate(requestDto, "test@example.com");

        // then
        assertThat(result).isEqualTo(1L);
        verify(codePostBookmarkCommand).trySave(requestDto, mockMember);
    }

    @Test
    void saveOrUpdate_UNIQUE_제약조건_위반시_업데이트로_전환한다() {
        // given
        CodePostBookmarkSaveRequestDto requestDto = new CodePostBookmarkSaveRequestDto(123L);
        when(memberCommand.findByEmail("test@example.com")).thenReturn(mockMember);
        when(codePostBookmarkCommand.trySave(requestDto, mockMember)).thenThrow(DataIntegrityViolationException.class);
        when(codePostBookmarkCommand.fallbackToUpdate(123L, 1L)).thenReturn(2L);

        // when
        Long result = codePostBookmarkService.saveOrUpdate(requestDto, "test@example.com");

        // then
        assertThat(result).isEqualTo(2L);
        verify(codePostBookmarkCommand).fallbackToUpdate(123L, 1L);
    }

//    @Test
//    void findAllByMemberId_정상조회() {
//        // given
//        CodePostBookmarkResponseDto dto1 = new CodePostBookmarkResponseDto(1L, "Post 1");
//        CodePostBookmarkResponseDto dto2 = new CodePostBookmarkResponseDto(2L, "Post 2");
//
//        var dtoList = List.of(dto1, dto2);
//        Page<CodePostBookmarkResponseDto> page = new PageImpl<>(dtoList, PageRequest.of(0, 10), 2);
//
//        when(memberCommand.findByEmail("test@example.com")).thenReturn(mockMember);
//        when(codePostBookmarkCommand.findAllByMemberId(1L, PageRequest.of(0, 10))).thenReturn(page);
//
//        // when
//        CodePostBookmarkListResponseDto result = codePostBookmarkService.findAllByMemberId("test@example.com", PageRequest.of(0, 10));
//
//        // then
//        assertThat(result.dtoList().size()).isEqualTo(2);
//        assertThat(result.totalPage()).isEqualTo(1);
//    }

    @Test
    void deleteExpiredSoftDeletedBookmarks_정상작동() {
        // when
        codePostBookmarkService.deleteExpiredSoftDeletedBookmarks();

        // then
        verify(codePostBookmarkCommand).deleteExpiredSoftDeletedBookmarks();
    }
}