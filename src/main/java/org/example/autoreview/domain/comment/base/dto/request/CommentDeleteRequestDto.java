package org.example.autoreview.domain.comment.base.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentDeleteRequestDto(

        @Schema(description = "댓글 아이디")
        Long commentId,

        @Schema(description = "작성자 아이디")
        Long writerId
) {
}
