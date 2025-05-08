package org.example.autoreview.domain.codepost.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;

@Getter
public class CodePostThumbnailResponseDto {

    private final Long id;
    private final Long writerId;
    private final String writerEmail;
    private final String writerNickName;
    private final String title;
    private final int level;
    private final boolean isPublic;
    private final String description;
    private final LocalDateTime createdDate;

    public CodePostThumbnailResponseDto(CodePost entity, Member writer) {
        this.id = entity.getId();
        this.writerId = entity.getWriterId();
        this.writerEmail = writer.getEmail();
        this.writerNickName = writer.getNickname();
        this.title = entity.getTitle();
        this.level = entity.getLevel();
        this.isPublic = entity.isPublic();
        this.description = entity.getDescription();
        this.createdDate = entity.getCreateDate();
    }
}
