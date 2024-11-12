package org.example.autoreview.domain.codepost.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class CodePostListResponseDto {

    private final List<CodePostThumbnailResponseDto> dtoList;
    private final int totalPage;

    public CodePostListResponseDto(List<CodePostThumbnailResponseDto> dtoList, int totalPage) {
        this.dtoList = dtoList;
        this.totalPage = totalPage;
    }
}
