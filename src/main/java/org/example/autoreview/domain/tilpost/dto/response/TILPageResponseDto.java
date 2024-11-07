package org.example.autoreview.domain.tilpost.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.tilpost.entity.TILPost;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TILPageResponseDto {

    private List<TILPostThumbnailResponseDto> list;
    private int totalPage;

    public TILPageResponseDto(Page<TILPost> entity){
        this.list = entity.getContent().stream()
                .map(TILPostThumbnailResponseDto::new)
                .collect(Collectors.toList());
        this.totalPage = entity.getTotalPages();
    }

}
