package org.example.autoreview.domain.comment.base.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.comment.base.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;

    @Schema(nullable = true)
    private Long parentId;

    private Long writerId;

    private String writerNickName;

    private String writerEmail;

    private String mentionNickName;

    private String mentionEmail;

    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public CommentResponseDto(Comment entity, String body, String writerEmail, String writerNickName) {
        this.id = entity.getId();
        this.parentId = entity.getParentId();
        this.writerId = entity.getWriterId();
        this.writerNickName = writerNickName;
        this.writerEmail = writerEmail;
        this.mentionNickName = entity.getMentionNickName();
        this.mentionEmail = entity.getMentionEmail();
        this.body = body;
        this.createdAt = entity.getCreateDate();
        this.updatedAt = entity.getUpdateDate();
    }
}
