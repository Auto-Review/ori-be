package org.example.autoreview.domain.tilpost.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.tilpost.entity.TILPost;

@Getter
public class TILPostResponseDto {

    private final Long id;

    private final String title;

    private final String content;

    public TILPostResponseDto(TILPost entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
}
