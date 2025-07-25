package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import static java.util.List.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkListResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CodePostBookmarkServiceUnitTest {

    @Mock
    private CodePostBookmarkCommand codePostBookmarkCommand;

    @InjectMocks
    private CodePostBookmarkService codePostBookmarkService;

    private Member mockMember;

    private CodePostBookmark mockCodePostBookmark;

    private String email;

    private Long codePostId;

    @BeforeEach
    void setUp() {
        Long memberId = 1L;
        Long bookmarkId = 1L;
        codePostId = 123L;
        email = "test@example.com";

        mockMember = Member.builder()
                .nickname("tester")
                .role(Role.USER)
                .email(email)
                .build();

        mockCodePostBookmark = CodePostBookmark.builder()
                .email(mockMember.getEmail())
                .codePostId(codePostId)
                .isDeleted(false)
                .build();

        ReflectionTestUtils.setField(mockMember,"id", memberId);
        ReflectionTestUtils.setField(mockCodePostBookmark,"id", bookmarkId);
    }

    @Test
    void saveOrUpdate_성공적으로_북마크를_저장한다() {
        // given
        CodePostBookmarkSaveRequestDto requestDto = new CodePostBookmarkSaveRequestDto(codePostId);
        when(codePostBookmarkCommand.saveOrUpdate(requestDto, mockMember.getEmail()))
                .thenReturn(Optional.ofNullable(mockCodePostBookmark));

        // when
        Long result = codePostBookmarkService.saveOrUpdate(requestDto, email);

        // then
        assertThat(result).isEqualTo(mockCodePostBookmark.getId());
        verify(codePostBookmarkCommand).saveOrUpdate(requestDto, mockMember.getEmail());
    }

    @Test
    void saveOrUpdate_UNIQUE_제약조건_위반시_업데이트로_전환한다() {
        // given
        CodePostBookmark updateBookmark = CodePostBookmark.builder()
                .email(mockMember.getEmail())
                .codePostId(codePostId)
                .isDeleted(!mockCodePostBookmark.isDeleted())
                .build();
        ReflectionTestUtils.setField(updateBookmark,"id",2L);

        CodePostBookmarkSaveRequestDto requestDto = new CodePostBookmarkSaveRequestDto(codePostId);

        when(codePostBookmarkCommand.saveOrUpdate(requestDto, mockMember.getEmail())).thenReturn(
                Optional.of(updateBookmark));

        // when
        Long result = codePostBookmarkService.saveOrUpdate(requestDto, email);

        // then
        assertThat(result).isEqualTo(2L);
        verify(codePostBookmarkCommand).saveOrUpdate(requestDto, mockMember.getEmail());
    }

    @Test
    void findAllByEmail_정상조회() {
        // given
        CodePostBookmark bookmark1 = CodePostBookmark.builder()
                .codePostId(1L)
                .email(mockMember.getEmail())
                .isDeleted(false)
                .build();

        CodePostBookmark bookmark2 = CodePostBookmark.builder()
                .codePostId(2L)
                .email(mockMember.getEmail())
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

        when(codePostBookmarkCommand.findAllByEmail(email, PageRequest.of(0, 10))).thenReturn(page);

        // when
        CodePostBookmarkListResponseDto result = codePostBookmarkService.findAllByEmail(email, PageRequest.of(0, 10));

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