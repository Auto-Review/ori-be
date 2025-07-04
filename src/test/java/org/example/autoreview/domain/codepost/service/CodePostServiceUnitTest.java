package org.example.autoreview.domain.codepost.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.bookmark.CodePostBookmark.service.CodePostBookmarkCommand;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.Language;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.domain.notification.entity.Notification;
import org.example.autoreview.domain.notification.service.NotificationCommand;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CodePostServiceUnitTest {

    @Mock
    private CodePostCommand codePostCommand;

    @Mock
    private MemberCommand memberCommand;

    @Mock
    private NotificationCommand notificationCommand;

    @Mock
    private CodePostBookmarkCommand codePostBookmarkCommand;

    @InjectMocks
    private CodePostService codePostService;

    private Long codePostId;
    private Long memberId;
    private String email;
    private Member mockMember;
    private CodePost mockCodePost;

    @BeforeEach
    void setUp() {
        codePostId = 123L;
        memberId = 1L;
        email = "test@example.com";
        mockMember = Member.builder()
                .email(email)
                .nickname("tester")
                .build();

        mockCodePost = CodePost.builder()
                .writerId(memberId)
                .language(Language.JAVA)
                .isPublic(false)
                .build();

        ReflectionTestUtils.setField(mockMember, "id", memberId);
        ReflectionTestUtils.setField(mockCodePost, "id", codePostId);
    }

    @Test
    void save_post_review_day_is_null() {
        // given
        CodePostSaveRequestDto requestDto = new CodePostSaveRequestDto(
                "test",
                1,
                true,
                null,
                "test",
                "java",
                "import test"
        );
        when(memberCommand.findByEmail(email)).thenReturn(mockMember);
        when(codePostCommand.save(any(CodePost.class))).thenReturn(mockCodePost);


        // when
        Long result = codePostService.save(requestDto, email);

        // then
        assertThat(result).isEqualTo(codePostId);
        verify(notificationCommand, never()).save(any(Notification.class));
    }

    @Test
    void save_post_review_day_is_not_null() {
        // given
        LocalDate reviewDay = LocalDate.now().plusDays(2);
        CodePostSaveRequestDto requestDto = new CodePostSaveRequestDto(
                "test",
                1,
                true,
                reviewDay,
                "test",
                "java",
                "import test"
        );
        when(memberCommand.findByEmail(email)).thenReturn(mockMember);
        when(codePostCommand.save(any(CodePost.class))).thenReturn(mockCodePost);
        doNothing().when(notificationCommand).save(any(Notification.class));


        // when
        Long result = codePostService.save(requestDto, email);

        // then
        assertThat(result).isEqualTo(codePostId);
        verify(notificationCommand).save(any(Notification.class));
    }

    @Test
    void 비공개_포스트를_다른_사용자가_조회() {
        //given
        Long userId = 10L;
        Member mockUser = Member.builder()
                .email("user@example.com")
                .nickname("user")
                .build();

        ReflectionTestUtils.setField(mockUser, "id", userId);

        when(memberCommand.findByEmail(email)).thenReturn(mockUser);
        when(codePostCommand.findByIdIsPublic(mockCodePost.getId(), mockUser.getId()))
                .thenThrow(CustomRuntimeException.class);

        //when + then
        assertThrows(CustomRuntimeException.class,
                () -> codePostService.findById(mockCodePost.getId(), email)
        );
    }

    @Test
    void 비공개_포스트를_작성자가_조회() {
        //given
        CodePostBookmark mockBookmark = CodePostBookmark.builder()
                .email(mockMember.getEmail())
                .codePostId(codePostId)
                .isDeleted(false)
                .build();

        ReflectionTestUtils.setField(mockBookmark, "id", 1L);

        when(memberCommand.findByEmail(email)).thenReturn(mockMember);
        when(codePostCommand.findByIdIsPublic(codePostId, memberId)).thenReturn(mockCodePost);
        when(memberCommand.findById(memberId)).thenReturn(mockMember);
        when(codePostBookmarkCommand.findByCodePostBookmark(email,codePostId)).thenReturn(Optional.empty());

        //when
        CodePostResponseDto responseDto = codePostService.findById(codePostId, email);

        //then
        assertThat(responseDto.getWriterEmail()).isEqualTo(mockMember.getEmail());
        assertThat(responseDto.getWriterNickName()).isEqualTo(mockMember.getNickname());
    }
}