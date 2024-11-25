package org.example.autoreview.domain.codepost.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.dto.MemberResponseDto;

@Getter
public class CodePostThumbnailResponseDto {

    private final Long id;
    private final String title;
    private final int level;
    private final String description;
    private final MemberResponseDto member;
    private final LocalDateTime createdDate;

    public CodePostThumbnailResponseDto(CodePost entity, String description) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.level = entity.getLevel();
        this.description = description;
        this.member = new MemberResponseDto(entity.getMember());
        this.createdDate = entity.getCreateDate();
    }
}
