package org.example.autoreview.domain.codepost.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.autoreview.domain.codepost.entity.CodePost;

@Getter
public class CodePostThumbnailResponseDto {

    private final Long id;
    private final Long writerId;
    private final String writerEmail;
    private final String writerNickName;
    private final String title;
    private final int level;
    private final String description;
    private final LocalDateTime createdDate;

    public CodePostThumbnailResponseDto(CodePost entity, String description) {
        this.id = entity.getId();
        this.writerId = entity.getWriterId();
        this.writerEmail = entity.getWriterEmail();
        this.writerNickName = entity.getWriterNickName();
        this.title = entity.getTitle();
        this.level = entity.getLevel();
        this.description = description;
        this.createdDate = entity.getCreateDate();
    }
}
