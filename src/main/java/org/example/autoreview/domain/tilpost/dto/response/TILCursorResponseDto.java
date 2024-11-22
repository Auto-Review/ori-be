package org.example.autoreview.domain.tilpost.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class TILCursorResponseDto {

    private final List<TILPostThumbnailResponseDto> lists;
    private final Long cursorId;
    private final int pageSize;

    public TILCursorResponseDto(List<TILPostThumbnailResponseDto> lists, Long cursorId, int pageSize){
        this.lists = lists;
        this.cursorId = cursorId;
        this.pageSize = pageSize;
    }
}
