package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import static java.util.List.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkListResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.codepost.entity.CodePost;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CodePostBookmarkServiceUnitTest {

    @Mock
    private CodePostBookmarkCommand codePostBookmarkCommand;

    @Mock
    private MemberCommand memberCommand;

    @InjectMocks
    private CodePostBookmarkService codePostBookmarkService;

    private Member mockMember;

    private String email;

    private Long codePostId;

    @BeforeEach
    void setUp() {
        codePostId = 123L;
        email = "test@example.com";
        mockMember = Member.builder()
                .nickname("tester")
                .role(Role.USER)
                .email(email)
                .build();
    }

    @Test
    void saveOrUpdate_성공적으로_북마크를_저장한다() {
        // given
        CodePostBookmarkSaveRequestDto requestDto = new CodePostBookmarkSaveRequestDto(codePostId);
        when(memberCommand.findByEmail(email)).thenReturn(mockMember);
        when(codePostBookmarkCommand.trySave(requestDto, mockMember)).thenReturn(1L);

        // when
        Long result = codePostBookmarkService.saveOrUpdate(requestDto, email);

        // then
        assertThat(result).isEqualTo(1L);
        verify(codePostBookmarkCommand).trySave(requestDto, mockMember);
    }

    @Test
    void saveOrUpdate_UNIQUE_제약조건_위반시_업데이트로_전환한다() {
        // given
        CodePostBookmarkSaveRequestDto requestDto = new CodePostBookmarkSaveRequestDto(codePostId);
        when(memberCommand.findByEmail(email)).thenReturn(mockMember);
        when(codePostBookmarkCommand.trySave(requestDto, mockMember)).thenThrow(DataIntegrityViolationException.class);

        // mockMember 의 id값이 null이기 때문에 memberId에 null 삽입한 채로 진행
        when(codePostBookmarkCommand.fallbackToUpdate(codePostId, null)).thenReturn(2L);

        // when
        Long result = codePostBookmarkService.saveOrUpdate(requestDto, email);

        // then
        assertThat(result).isEqualTo(2L);
        verify(codePostBookmarkCommand).fallbackToUpdate(codePostId, null);
    }

    @Test
    void findAllByMemberId_정상조회() {
        // given
        CodePostBookmark bookmark1 = CodePostBookmark.builder()
                .codePostId(1L)
                .member(mockMember)
                .isDeleted(false)
                .build();

        CodePostBookmark bookmark2 = CodePostBookmark.builder()
                .codePostId(2L)
                .member(mockMember)
                .isDeleted(false)
                .build();

        CodePost codePost = CodePost.builder()
                .writerId(1L)
                .isPublic(true)
                .build();

        CodePostBookmarkResponseDto dto1 = new CodePostBookmarkResponseDto(bookmark1, codePost, mockMember);
        CodePostBookmarkResponseDto dto2 = new CodePostBookmarkResponseDto(bookmark2, codePost, mockMember);

        var dtoList = of(dto1, dto2);
        Page<CodePostBookmarkResponseDto> page = new PageImpl<>(dtoList, PageRequest.of(0, 10), 2);

        when(memberCommand.findByEmail(email)).thenReturn(mockMember);

        // mockMember 의 id값이 null이기 때문에 memberId에 null 삽입한 채로 진행
        when(codePostBookmarkCommand.findAllByMemberId(null, PageRequest.of(0, 10))).thenReturn(page);

        // when
        CodePostBookmarkListResponseDto result = codePostBookmarkService.findAllByMemberId(email, PageRequest.of(0, 10));

        // then
        assertThat(result.dtoList().size()).isEqualTo(2);
        assertThat(result.totalPage()).isEqualTo(1);
    }

    @Test
    void deleteExpiredSoftDeletedBookmarks_정상작동() {
        // when
        codePostBookmarkService.deleteExpiredSoftDeletedBookmarks();

        // then
        verify(codePostBookmarkCommand).deleteExpiredSoftDeletedBookmarks();
    }
}