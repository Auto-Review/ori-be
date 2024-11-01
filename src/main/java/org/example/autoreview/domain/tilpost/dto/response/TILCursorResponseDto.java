package org.example.autoreview.domain.tilpost.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.tilpost.entity.TILPost;

import java.util.List;

@Getter
public class TILCursorResponseDto {
    private List<TILPostListResponseDto> lists;
    private Long cursorId;
    private int pageSize;

    public TILCursorResponseDto(List<TILPostListResponseDto> lists, Long cursorId, int pageSize){
        this.lists = lists;
        this.cursorId =cursorId;
        this.pageSize = pageSize;
    }
}
