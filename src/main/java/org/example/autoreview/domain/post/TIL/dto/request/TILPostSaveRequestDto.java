package org.example.autoreview.domain.post.TIL.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.example.autoreview.domain.post.TIL.entity.TILPost;

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
