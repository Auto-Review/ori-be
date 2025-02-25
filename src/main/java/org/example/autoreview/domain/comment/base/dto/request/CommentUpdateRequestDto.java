package org.example.autoreview.domain.comment.base.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentUpdateRequestDto(

        @Schema(description = "댓글 아이디")
        Long commentId,

        @Schema(description = "작성자 닉네임")
        String writerNickName,

        @Schema(description = "작성자 이메일")
        String writerEmail,

        @Schema(description = "댓글 내용")
        String body,

        @Schema(description = "공개 여부")
        boolean isPublic,

        @Schema(description = "언급 아이디", nullable = true)
        String mentionNickName,

        @Schema(description = "언급된 대상 이메일", nullable = true)
        String mentionEmail
) {
}
