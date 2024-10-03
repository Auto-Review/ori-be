package org.example.autoreview.domain.post.CODE.dto.request;

import lombok.Builder;
import org.example.autoreview.domain.post.CODE.entity.CodePost;

import java.time.LocalDateTime;

@Builder
public record CodePostSaveRequestDto(
        String title,
        int level,
        LocalDateTime reviewTime,
        String description,
        String code
) {
    public CodePost toEntity(){
        return CodePost.builder()
                .title(title)
                .level(level)
                .reviewTime(reviewTime)
                .description(description)
                .code(code)
                .build();
    }
}
