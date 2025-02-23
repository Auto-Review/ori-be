package org.example.autoreview.domain.codepost.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.review.dto.response.ReviewResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CodePostResponseDto {

    private final Long id;
    private final MemberResponseDto memberDto;
    private final String title;
    private final int level;
    private final LocalDate reviewDay;
    private final String description;
    private final String language;
    private final String code;
    private final List<ReviewResponseDto> dtoList;
    private final LocalDateTime createDate;

    public CodePostResponseDto(CodePost entity, List<ReviewResponseDto> dtoList) {
        this.id = entity.getId();
        this.memberDto = new MemberResponseDto(entity.getMember());
        this.title = entity.getTitle();
        this.level = entity.getLevel();
        this.reviewDay = entity.getReviewDay();
        this.description = entity.getDescription();
        this.language = entity.getLanguage().getType();
        this.code = entity.getCode();
        this.dtoList = dtoList;
        this.createDate = entity.getCreateDate();
    }
}
