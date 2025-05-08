package org.example.autoreview.domain.codepost.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class CodePostListResponseDto<T> {

    private final List<T> dtoList;
    private final int totalPage;

    public CodePostListResponseDto(List<T> dtoList, int totalPage) {
        this.dtoList = dtoList;
        this.totalPage = totalPage;
    }
}
