package org.example.autoreview.domain.comment.base.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.comment.base.Comment;

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

    public CommentResponseDto(Comment entity, String body) {
        this.id = entity.getId();
        this.parentId = entity.getParentId();
        this.writerId = entity.getWriterId();
        this.writerNickName = entity.getWriterNickName();
        this.writerEmail = entity.getWriterEmail();
        this.mentionNickName = entity.getMentionNickName();
        this.mentionEmail = entity.getMentionEmail();
        this.body = body;
    }
}
