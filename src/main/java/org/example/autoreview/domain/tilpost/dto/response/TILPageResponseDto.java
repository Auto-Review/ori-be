package org.example.autoreview.domain.tilpost.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class TILPageResponseDto {

    private final List<TILPostThumbnailResponseDto> dtoList;
    private final int totalPage;

    public TILPageResponseDto(List<TILPostThumbnailResponseDto> dtoList, int totalPage){
        this.dtoList = dtoList;
        this.totalPage = totalPage;
    }

}
