package org.example.autoreview.domain.post.TIL.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.post.TIL.entity.TILPost;

@Getter
public class TILPostResponseDto {

    private Long id;

    private String title;

    private String content;

    public TILPostResponseDto(TILPost entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
}
