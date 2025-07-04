package org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response;

import java.util.List;

public record CodePostBookmarkListResponseDto(
        List<CodePostBookmarkResponseDto> dtoList,
        int totalPage
) {
}
