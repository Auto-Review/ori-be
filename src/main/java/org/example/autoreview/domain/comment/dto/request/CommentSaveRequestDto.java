package org.example.autoreview.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.comment.entity.Comment;
import org.example.autoreview.domain.member.entity.Member;

public record CommentSaveRequestDto(

        @Schema(description = "게시글 아이디")
        Long codePostId,

        @Schema(description = "본문", example = "이 코드 이게 맞아요?")
        String body,

        @Schema(description = "언급한 닉네임", nullable = true)
        String targetNickName,

        @Schema(description = "상위 댓글 아이디", nullable = true)
        Long parentId
) {
    public Comment toCommentEntity(CodePost codePost, Member writer) {
        return Comment.builder()
                .codePost(codePost)
                .writerId(writer.getId())
                .writerNickName(writer.getNickname())
                .body(body)
                .targetNickName(targetNickName)
                .build();
    }

    public Comment toReplyEntity(CodePost codePost, Comment parent, Member writer) {
        return Comment.builder()
                .codePost(codePost)
                .parent(parent)
                .writerId(writer.getId())
                .writerNickName(writer.getNickname())
                .targetNickName(targetNickName)
                .body(body)
                .build();
    }
}
