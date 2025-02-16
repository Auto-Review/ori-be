package org.example.autoreview.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentUpdateRequestDto(

        @Schema(description = "댓글 아이디")
        Long commentId,

        @Schema(description = "작성자 닉네임")
        String writerNickName,

        @Schema(description = "댓글 내용")
        String body,

        @Schema(description = "언급 아이디", nullable = true)
        String targetNickName
) {
}
