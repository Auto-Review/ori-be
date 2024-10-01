package org.example.autoreview.domain.post.CODE.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.post.CODE.entity.CodePost;

import java.time.LocalDateTime;

@Getter
public class CodePostResponseDto {

    private final Long id;
    private final String title;
    private final int level;
    private final LocalDateTime reviewTime;
    private final String content;
    private final String code;

    public CodePostResponseDto(CodePost entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.level = entity.getLevel();
        this.reviewTime = entity.getReviewTime();
        this.content = entity.getContent();
        this.code = entity.getCode();
    }
}
