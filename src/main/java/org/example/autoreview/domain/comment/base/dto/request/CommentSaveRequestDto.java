package org.example.autoreview.domain.comment.base.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;
import org.example.autoreview.domain.comment.til.entity.TilPostComment;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.tilpost.entity.TILPost;

public record CommentSaveRequestDto(

        @Schema(description = "게시글 아이디")
        Long postId,

        @Schema(description = "본문", example = "이 코드 이게 맞아요?")
        String body,

        @Schema(description = "공개 여부")
        boolean isPublic,

        @Schema(description = "언급한 닉네임", nullable = true)
        String mentionNickName,

        @Schema(description = "언급된 대상 이메일", nullable = true)
        String mentionEmail,

        @Schema(description = "상위 댓글 아이디", nullable = true)
        Long parentId
) {
    public CodePostComment toCodePostCommentEntity(CodePost codePost, Member writer) {
        return CodePostComment.builder()
                .codePost(codePost)
                .writerId(writer.getId())
                .body(body)
                .isPublic(isPublic)
                .build();
    }

    public CodePostComment toCodePostReplyEntity(CodePost codePost, CodePostComment parent, Member writer) {
        return CodePostComment.builder()
                .codePost(codePost)
                .parent(parent)
                .writerId(writer.getId())
                .mentionNickName(mentionNickName)
                .mentionEmail(mentionEmail)
                .body(body)
                .isPublic(isPublic)
                .build();
    }

    public TilPostComment toTilPostCommentEntity(TILPost tilPost, Member writer) {
        return TilPostComment.builder()
                .tilPost(tilPost)
                .writerId(writer.getId())
                .body(body)
                .isPublic(isPublic)
                .build();
    }

    public TilPostComment toTilPostReplyEntity(TILPost tilPost, TilPostComment parent, Member writer) {
        return TilPostComment.builder()
                .tilPost(tilPost)
                .parent(parent)
                .writerId(writer.getId())
                .mentionNickName(mentionNickName)
                .mentionEmail(mentionEmail)
                .body(body)
                .isPublic(isPublic)
                .build();
    }
}
