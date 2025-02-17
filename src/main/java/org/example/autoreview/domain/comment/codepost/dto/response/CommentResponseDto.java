package org.example.autoreview.domain.comment.codepost.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    @Schema(nullable = true)
    private Long parentId;

    private Long writerId;

    private String writerNickName;

    private String targetNickName;

    private String body;

    public CommentResponseDto(CodePostComment entity) {
        //TODO: getParent() 가 불필요한 객체 조회이지 않나 확인해봐야함
        this.parentId = entity.getParent() != null ? entity.getParent().getId() : null;
        this.writerId = entity.getWriterId();
        this.writerNickName = entity.getWriterNickName();
        this.targetNickName = entity.getTargetNickName();
        this.body = entity.getBody();
    }
}
