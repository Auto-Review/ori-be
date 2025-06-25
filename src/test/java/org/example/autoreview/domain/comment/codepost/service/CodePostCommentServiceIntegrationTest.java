package org.example.autoreview.domain.comment.codepost.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.data.domain.PageRequest.of;

import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.Language;
import org.example.autoreview.domain.codepost.service.CodePostCommand;
import org.example.autoreview.domain.comment.base.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.base.dto.response.CommentListResponseDto;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.domain.member.entity.Role;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("${spring.profiles.active:test}")
@SpringBootTest
public class CodePostCommentServiceIntegrationTest {

    @Autowired
    private CodePostCommentCommand codePostCommentCommand;

    @Autowired
    private CodePostCommand codePostCommand;

    @Autowired
    private MemberCommand memberCommand;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CodePostCommentService codePostCommentService;

    private String email;
    private Member testMember;
    private CodePost testCodePost;
    private CommentSaveRequestDto parentRequestDto;

    @BeforeEach
    void setUp() {
        email = "test@example.com";
        testMember = memberRepository.save(Member.builder()
                .email(email)
                .nickname("tester")
                .role(Role.USER)
                .build());

        testCodePost = codePostCommand.save(CodePost.builder()
                .writerId(testMember.getId())
                .language(Language.ALL)
                .isPublic(true)
                .build());

        parentRequestDto = new CommentSaveRequestDto(
                testCodePost.getId(),
                "test",
                false,
                null,
                null,
                null
        );
    }

    @Test
    void save_comment() {
        // when
        Long commentId = codePostCommentService.save(parentRequestDto,email);
        CodePostComment comment = codePostCommentCommand.findById(commentId);

        // then
        assertThat(commentId).isEqualTo(1L);
        assertThat(comment.getBody()).isEqualTo("test");
    }

    @Test
    void save_reply() {
        // given
        Long parentCommentId = codePostCommentService.save(parentRequestDto,email);

        CommentSaveRequestDto child = new CommentSaveRequestDto(
                testCodePost.getId(),
                "child test",
                false,
                testMember.getNickname(),
                testMember.getEmail(),
                parentCommentId
        );

        // when
        Long childCommentId = codePostCommentService.save(child,email);
        CodePostComment parentComment = codePostCommentCommand.findById(parentCommentId);
        CodePostComment childComment = codePostCommentCommand.findById(childCommentId);

        // then
        assertThat(childCommentId).isEqualTo(2L);
        assertThat(childComment.getBody()).isEqualTo("child test");
        assertThat(childComment.getParentId()).isEqualTo(parentComment.getId());
    }

    @Test
    void userFindCommentPage() {
        // given
        Long parentCommentId = codePostCommentService.save(parentRequestDto,email);

        // when
        CommentListResponseDto parentComment = codePostCommentService.userFindCommentPage(testCodePost.getId(),of(0, 10), email);
    }



}
