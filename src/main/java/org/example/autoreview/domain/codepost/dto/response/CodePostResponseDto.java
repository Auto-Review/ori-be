package org.example.autoreview.domain.codepost.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.codepost.entity.CodePost;

import java.time.LocalDate;

@Getter
public class CodePostResponseDto {

    private final Long id;
    private final String title;
    private final int level;
    private final LocalDate reviewDay;
    private final String description;
    private final String code;

    public CodePostResponseDto(CodePost entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.level = entity.getLevel();
        this.reviewDay = entity.getReviewDay();
        this.description= entity.getDescription();
        this.code = entity.getCode();
    }
}
