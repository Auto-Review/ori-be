package org.example.autoreview.domain.post.TIL.dto;

import lombok.Getter;
import org.example.autoreview.domain.post.TIL.entity.TILPost;

@Getter
public class TILPostSaveDto {

    private String title;

    private String content;

    public TILPost toEntity() {
        return TILPost.builder()
                .title(title)
                .content(content)
                .build();
    }
}
