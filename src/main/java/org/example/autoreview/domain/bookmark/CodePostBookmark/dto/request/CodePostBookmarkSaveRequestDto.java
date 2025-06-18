package org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CodePostBookmarkSaveRequestDto(

        @Schema(description = "코드 포스트 아이디", example = "1")
        Long codePostId
) {
}
