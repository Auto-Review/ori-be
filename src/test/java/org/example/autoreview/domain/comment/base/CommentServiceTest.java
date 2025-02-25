package org.example.autoreview.domain.comment.base;

import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.CodePostRepository;
import org.example.autoreview.domain.codepost.entity.Language;
import org.example.autoreview.domain.comment.base.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.base.dto.response.CommentListResponseDto;
import org.example.autoreview.domain.comment.codepost.service.CodePostCommentService;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.Role;
import org.example.autoreview.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CodePostCommentService codePostCommentService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CodePostRepository codePostRepository;

    private Member testMember;

    @BeforeEach
    void setUp() {
        // 테스트용 멤버 생성
        testMember = Member.builder()
                .email("abc@naver.com")
                .nickname("kim")
                .role(Role.USER)
                .build();

        memberService.saveOrFind(testMember.getEmail());

        Member member = memberService.findEntityById(1L);

        CodePost testCodePost = CodePost.builder()
                .title("title")
                .code("code")
                .member(member)
                .level(5)
                .language(Language.JAVA)
                .description("description")
                .reviewDay(LocalDate.parse("2024-10-11"))
                .build();

        codePostRepository.save(testCodePost);
    }

    @Test
    public void findByCommentPage_응답속도_테스트() {
        // Arrange
        int dataCount = 10000;
        Optional<CodePost> codePost = codePostRepository.findById(1L); // 생성된 CodePost의 ID 사용
        Pageable pageable = PageRequest.of(0, 6);

        // 대량 댓글 생성 (서비스 로직 사용)
        for (int i = 0; i < dataCount; i++) {
            CommentSaveRequestDto dto = new CommentSaveRequestDto(
                    codePost.get().getId(),
                    "테스트 댓글 " + i,
                    true,
                    null,
                    null,
                    null
            );
            codePostCommentService.save(dto, testMember.getEmail()); // 멤버의 이메일 사용
        }

        // Act & Measure Time
        Instant start = Instant.now();
        CommentListResponseDto result = codePostCommentService.userFindCommentPage(
                codePost.get().getId(),
                pageable,
                testMember.getEmail()
        );
        Instant end = Instant.now();

        // Assert
        long elapsedTime = Duration.between(start, end).toMillis();
        System.out.println("Elapsed time for fetching comments: " + elapsedTime + " ms");

        assertEquals((dataCount + 6 - 1)/6, result.totalPage());
    }
}
