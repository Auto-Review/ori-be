package org.example.autoreview.domain.codepost.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.example.autoreview.domain.bookmark.CodePostBookmark.service.CodePostBookmarkCommand;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.CodePostRepository;
import org.example.autoreview.domain.codepost.entity.Language;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.domain.member.entity.Role;
import org.example.autoreview.domain.notification.service.NotificationCommand;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("${spring.profiles.active:test}")
@SpringBootTest
public class CodePostServiceIntegrationTest {

    @Autowired
    private CodePostCommand codePostCommand;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CodePostRepository codePostRepository;

    @Autowired
    CodePostBookmarkCommand codePostBookmarkCommand;

    @Autowired
    private NotificationCommand notificationCommand;

    @Autowired
    private CodePostService codePostService;

    private CodePostSaveRequestDto saveRequestDto;
    private Member testMember;
    private CodePost testCodePost;

    @BeforeEach
    void setUp() {
        testMember = memberRepository.save(Member.builder()
                .email("test@example.com")
                .nickname("tester")
                .role(Role.USER)
                .build());

        testCodePost = codePostCommand.save(CodePost.builder()
                .writerId(testMember.getId())
                .language(Language.JAVA)
                .isPublic(false)
                .build());
    }

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
        codePostRepository.deleteAll();
    }

    @Test
    void save_post_review_day_is_null() {
        // given
        saveRequestDto = new CodePostSaveRequestDto(
                "test",
                1,
                true,
                null,
                "test",
                "java",
                "import test"
        );

        // when
        Long codePostId = codePostService.save(saveRequestDto, testMember.getEmail());

        // then
        CodePostResponseDto responseDto = codePostService.findById(codePostId, testMember.getEmail());

        assertThat(responseDto.getReviewDay()).isNull();
        assertThat(responseDto.getWriterId()).isEqualTo(testMember.getId());
    }

    @Test
    void save_post_review_day_is_not_null() {
        // given
        saveRequestDto = new CodePostSaveRequestDto(
                "test",
                1,
                true,
                LocalDate.now().plusDays(1),
                "test",
                "java",
                "import test"
        );

        // when
        Long codePostId = codePostService.save(saveRequestDto, testMember.getEmail());

        // then
        CodePostResponseDto responseDto = codePostService.findById(codePostId, testMember.getEmail());

        assertThat(notificationCommand.existsByCodePostId(codePostId)).isTrue();
        assertThat(responseDto.getReviewDay()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(responseDto.getWriterId()).isEqualTo(testMember.getId());

    }

    @Test
    void 비공개_포스트를_다른_사용자가_조회() {
        // given
        Member user = memberRepository.save(Member.builder()
                .email("user@example.com")
                .nickname("user")
                .role(Role.USER)
                .build());

        // when + then
        assertThrows(CustomRuntimeException.class,
                () -> codePostService.findById(testCodePost.getId(), user.getEmail())
        );
    }

    @Test
    void 비공개_포스트를_작성자가_조회() {
        // when
        CodePostResponseDto responseDto = codePostService.findById(testCodePost.getId(), testMember.getEmail());

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getWriterId()).isEqualTo(testMember.getId());
    }

    @Test
    void update() {
        // given
        CodePostUpdateRequestDto requestDto = CodePostUpdateRequestDto.builder()
                .id(testCodePost.getId())
                .language("cpp")
                .build();

        String beforeLanguage = testCodePost.getLanguage().getType();

        // when
        Long updatePostId = codePostService.update(requestDto, testMember.getEmail());
        CodePost updatePost = codePostRepository.findById(updatePostId).get();

        // then
        assertThat(updatePost.getLanguage().getType()).isNotEqualTo(beforeLanguage);
        assertThat(updatePost.getLanguage()).isEqualTo(Language.CPP);
    }

    @Test
    void delete() {
        // given
        saveRequestDto = new CodePostSaveRequestDto(
                "test",
                1,
                true,
                LocalDate.now().plusDays(1),
                "test",
                "java",
                "import test"
        );

        // when
        Long codePostId = codePostService.save(saveRequestDto, testMember.getEmail());

        // when
        int notificationCount = notificationCommand.findAll().size();

        //then
        codePostService.delete(codePostId, testMember.getEmail());

        assertThat(notificationCount).isOne();
        assertThat(notificationCommand.findAll().size()).isZero();
    }
}
