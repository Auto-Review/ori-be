package org.example.autoreview.domain.tilpost.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.example.autoreview.domain.tilpost.entity.TILPost;

@Getter
@Builder
public class TILPostSaveRequestDto {

    private String title;

    private String content;

    public TILPost toEntity(){
        return TILPost.builder()
                .title(title)
                .content(content)
                .build();
    }
}
