package org.example.autoreview.domain.comment.codepost.service;

import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.Language;
import org.example.autoreview.domain.codepost.service.CodePostCommand;
import org.example.autoreview.domain.comment.base.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CodePostCommentServiceUnitTest {

    @Mock
    private CodePostCommentCommand codePostCommentCommand;

    @Mock
    private CodePostCommand codePostCommand;

    @Mock
    private MemberCommand memberCommand;

    @InjectMocks
    private CodePostCommentService codePostCommentService;

    private Long memberId;
    private String email;
    private Member mockMember;

    private Long codePostId;
    private CodePost mockCodePost;

    private Long commentId;
    private CodePostComment mockComment;

    @BeforeEach
    void setUp() {
        commentId = 1L;
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

        mockComment = CodePostComment.builder()
                .codePost(mockCodePost)
                .writerId(memberId)
                .body("test")
                .isPublic(false)
                .build();

        ReflectionTestUtils.setField(mockMember, "id", memberId);
        ReflectionTestUtils.setField(mockCodePost, "id", codePostId);
        ReflectionTestUtils.setField(mockComment, "id", 1L);
    }

    @Test
    void save_comment() {
        // given
        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(
                codePostId,
                null,
                false,
                null,
                null,
                null
        );

        CodePostComment comment = CodePostComment.builder()
                .codePost(mockCodePost)
                .writerId(memberId)
                .body("test")
                .isPublic(false)
                .build();
        ReflectionTestUtils.setField(comment, "id", 1L);
        when(memberCommand.findByEmail(email)).thenReturn(mockMember);
        when(codePostCommand.findById(codePostId)).thenReturn(mockCodePost);
        when(codePostCommentCommand.save(any())).thenReturn(comment);

        // when
        Long commentId = codePostCommentService.save(requestDto, email);

        // then
        assertThat(commentId).isEqualTo(1L);
        assertThat(mockCodePost).isEqualTo(comment.getCodePost());
    }

    @Test
    void save_reply() {
        // given
        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(
                codePostId,
                null,
                false,
                null,
                null,
                commentId
        );

        CodePostComment reply = CodePostComment.builder()
                .codePost(mockCodePost)
                .writerId(memberId)
                .body("test")
                .isPublic(false)
                .parent(mockComment)
                .build();

        ReflectionTestUtils.setField(reply, "id", 2L);
        when(memberCommand.findByEmail(email)).thenReturn(mockMember);
        when(codePostCommand.findById(codePostId)).thenReturn(mockCodePost);
        when(codePostCommentCommand.save(any())).thenReturn(reply);

        // when
        Long replyId = codePostCommentService.save(requestDto, email);

        // then
        assertThat(replyId).isEqualTo(2L);
        assertThat(reply.getParent()).isEqualTo(mockComment);
    }
}