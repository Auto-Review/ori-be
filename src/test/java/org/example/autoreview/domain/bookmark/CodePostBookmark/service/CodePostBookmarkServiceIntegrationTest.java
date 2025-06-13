package org.example.autoreview.domain.bookmark.CodePostBookmark.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.data.domain.PageRequest.of;

import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkListResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmarkRepository;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.CodePostRepository;
import org.example.autoreview.domain.codepost.service.CodePostCommand;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.domain.member.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CodePostBookmarkServiceIntegrationTest {

    @Autowired
    private CodePostBookmarkService codePostBookmarkService;

    @Autowired
    private CodePostCommand codePostCommand;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CodePostRepository codePostRepository;

    @Autowired
    private CodePostBookmarkRepository codePostBookmarkRepository;

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
                .writerId(testMember.getId())
                .isPublic(true)
                .build());

        saveRequestDto = new CodePostBookmarkSaveRequestDto(testCodePost.getId());
    }

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
        codePostRepository.deleteAll();
        codePostBookmarkRepository.deleteAll();
    }

    @Test
    void saveOrUpdate_trySave() {
        // when
        Long bookmarkId = codePostBookmarkService.saveOrUpdate(saveRequestDto, testMember.getEmail());

        // then
        CodePostBookmarkListResponseDto bookmarks = codePostBookmarkService.findAllByMemberId(testMember.getEmail(), of(0, 10));

        assertThat(bookmarkId).isNotNull();
        assertThat(bookmarks.dtoList().size()).isEqualTo(1);
        assertThat(bookmarks.dtoList().get(0).getCodePostId()).isEqualTo(testCodePost.getId());
    }

    @Test
    void saveOrUpdate_fallbackToUpdate() {
        // when
        // bookmark 여부 true -> false 로 변경
        Long bookmarkId = codePostBookmarkService.saveOrUpdate(saveRequestDto, testMember.getEmail());
        boolean beforeState = codePostBookmarkRepository.findById(bookmarkId).get().isDeleted();

        codePostBookmarkService.saveOrUpdate(saveRequestDto, testMember.getEmail());

        // then
        assertThat(beforeState).isFalse();
        assertThat(codePostBookmarkRepository.findById(bookmarkId).get().isDeleted()).isTrue();
    }

    @Test
    void saveOrUpdate_then_findAllByMemberId() {
        // given
        CodePostBookmarkSaveRequestDto requestDto = new CodePostBookmarkSaveRequestDto(testCodePost.getId());

        // when
        Long bookmarkId = codePostBookmarkService.saveOrUpdate(requestDto, testMember.getEmail());

        // then
        CodePostBookmarkListResponseDto bookmarks = codePostBookmarkService.findAllByMemberId(testMember.getEmail(), of(0, 10));

        assertThat(bookmarks.dtoList().size()).isEqualTo(1);
        assertThat(bookmarks.dtoList().get(0).getCodePostId()).isEqualTo(testCodePost.getId());
        assertThat(bookmarkId).isNotNull();
    }
}