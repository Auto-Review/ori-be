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

    private String targetNickName;

    private String body;

    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.parentId = entity.getParentId();
        this.writerId = entity.getWriterId();
        this.writerNickName = entity.getWriterNickName();
        this.targetNickName = entity.getTargetNickName();
        this.body = entity.getBody();
    }
}
