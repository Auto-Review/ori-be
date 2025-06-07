package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.data.domain.PageRequest.of;

import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkListResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.service.CodePostCommand;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.domain.member.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.profiles.active=test")
@Transactional
class CodePostBookmarkServiceIntegrationTest {

    @Autowired
    private CodePostBookmarkService service;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CodePostCommand codePostCommand;

    private CodePostBookmarkSaveRequestDto saveRequestDto;
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
                .writerId(1L)
                .isPublic(true)
                .build());

        saveRequestDto = new CodePostBookmarkSaveRequestDto(testCodePost.getId());
    }

    @Test
    void saveOrUpdate_trySave() {
        // when
        Long bookmarkId = service.saveOrUpdate(saveRequestDto, testMember.getEmail());

        // then
        CodePostBookmarkListResponseDto bookmarks = service.findAllByMemberId(testMember.getEmail(), of(0, 10));

        assertThat(bookmarkId).isNotNull();
        assertThat(bookmarks.dtoList().size()).isEqualTo(1);
        assertThat(bookmarks.dtoList().get(0).getCodePostId()).isEqualTo(testCodePost.getId());
    }

    @Test
    void saveOrUpdate_fallbackToUpdate() {
        // when
        // bookmark 여부 true -> false 로 변경
        Long saveFirstBookmarkId = service.saveOrUpdate(saveRequestDto, testMember.getEmail());
        Long saveSecondBookmarkId = service.saveOrUpdate(saveRequestDto, testMember.getEmail());

        // then
        CodePostBookmarkListResponseDto bookmarks = service.findAllByMemberId(testMember.getEmail(), of(0, 10));

        assertThat(bookmarks.dtoList().size()).isEqualTo(0);
        assertThat(saveFirstBookmarkId).isEqualTo(saveSecondBookmarkId);
    }


    @Test
    void saveOrUpdate_then_findAllByMemberId() {
        // given
        CodePostBookmarkSaveRequestDto requestDto = new CodePostBookmarkSaveRequestDto(testCodePost.getId());

        // when
        Long bookmarkId = service.saveOrUpdate(requestDto, testMember.getEmail());

        // then
        CodePostBookmarkListResponseDto bookmarks = service.findAllByMemberId(testMember.getEmail(), of(0, 10));

        assertThat(bookmarks.dtoList().size()).isEqualTo(1);
        assertThat(bookmarks.dtoList().get(0).getCodePostId()).isEqualTo(testCodePost.getId());
        assertThat(bookmarkId).isNotNull();
    }
}