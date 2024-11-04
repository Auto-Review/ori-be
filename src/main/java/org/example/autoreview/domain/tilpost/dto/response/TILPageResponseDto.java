package org.example.autoreview.domain.tilpost.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.autoreview.domain.tilpost.entity.TILPost;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TILPageResponseDto {
    private List<TILPostResponseDto> list;
    private int totalPage;

    @Builder
    public TILPageResponseDto(Page<TILPost> entity){
        this.list = entity.getContent().stream()
                .map(TILPostResponseDto::new)
                .collect(Collectors.toList());
        this.totalPage = entity.getTotalPages();
    }

}
