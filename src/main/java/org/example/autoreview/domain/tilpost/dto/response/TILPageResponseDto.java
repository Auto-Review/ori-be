package org.example.autoreview.domain.tilpost.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.tilpost.entity.TILPost;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TILPageResponseDto {

    private List<TILPostThumbnailResponseDto> dtoList;
    private int totalPage;

    public TILPageResponseDto(List<TILPostThumbnailResponseDto> dtoList, int totalPage){
        this.dtoList = dtoList;
        this.totalPage = totalPage;
    }

}
