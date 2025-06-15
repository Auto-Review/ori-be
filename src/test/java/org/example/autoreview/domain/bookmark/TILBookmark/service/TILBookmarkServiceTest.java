package org.example.autoreview.domain.bookmark.TILBookmark.service;

import org.example.autoreview.domain.bookmark.TILBookmark.entity.TILBookmark;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostThumbnailResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("local")
class TILBookmarkServiceTest {

    @Autowired
    private TILBookmarkService tilBookmarkService;

    @Test
    public void saveOrUpdate() throws Exception {
        //given
        String email = "abc@naver.com";
        Long postId = 1L;

        //when
        Long l = tilBookmarkService.saveOrUpdate(email, postId);
        TILBookmark bookmark = tilBookmarkService.findById(email, postId);
        //then
        assertThat(bookmark.getIsBookmarked()).isTrue();
    }
}