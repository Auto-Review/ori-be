package org.example.autoreview.domain.codepost.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.review.dto.response.ReviewResponseDto;

@Getter
public class CodePostResponseDto {

    private final Long id;
    private final Long writerId;
    private final String writerEmail;
    private final String writerNickName;
    private final String title;
    private final int level;
    private final boolean isPublic;
    private final LocalDate reviewDay;
    private final String description;
    private final String language;
    private final String code;
    private final List<ReviewResponseDto> dtoList;
    private final LocalDateTime createDate;

    public CodePostResponseDto(CodePost entity, List<ReviewResponseDto> dtoList, Member writer) {
        this.id = entity.getId();
        this.writerId = entity.getWriterId();
        this.writerEmail = writer.getEmail();
        this.writerNickName = writer.getNickname();
        this.title = entity.getTitle();
        this.level = entity.getLevel();
        this.isPublic = entity.isPublic();
        this.reviewDay = entity.getReviewDay();
        this.description = entity.getDescription();
        this.language = entity.getLanguage().getType();
        this.code = entity.getCode();
        this.dtoList = dtoList;
        this.createDate = entity.getCreateDate();
    }
}
