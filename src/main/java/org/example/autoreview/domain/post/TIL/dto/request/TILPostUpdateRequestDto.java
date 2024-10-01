package org.example.autoreview.domain.post.TIL.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TILPostUpdateRequestDto {

    private Long id;

    private String title;

    private String content;

    @Builder
    public TILPostUpdateRequestDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
