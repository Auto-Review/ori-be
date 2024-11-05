package org.example.autoreview.domain.codepost.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class CodePostListResponseDto {

    private final List<CodePostResponseDto> codePostResponseDtoList;
    private final int totalPage;

    public CodePostListResponseDto(List<CodePostResponseDto> codePostResponseDtoList, int totalPage) {
        this.codePostResponseDtoList = codePostResponseDtoList;
        this.totalPage = totalPage;
    }
}
