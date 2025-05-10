package org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.member.entity.Member;

public record CodePostBookmarkSaveRequestDto(

        @Schema(description = "코드 포스트 아이디", example = "1")
        Long codePostId
) {
    public CodePostBookmark toEntity(Member member){
        return CodePostBookmark.builder()
                .codePostId(codePostId)
                .member(member)
                .isDeleted(false)
                .build();
    }
}
