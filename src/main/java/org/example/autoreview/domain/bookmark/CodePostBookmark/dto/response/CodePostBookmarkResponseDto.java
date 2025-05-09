package org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.autoreview.domain.bookmark.CodePostBookmark.entity.CodePostBookmark;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;

@Getter
public class CodePostBookmarkResponseDto {

    private final Long id;
    private final String codePostTitle;
    private final int commentCount;
    private final String writer;
    private final LocalDateTime updateAt;

    public CodePostBookmarkResponseDto(CodePostBookmark bookmark, CodePost codePost, Member member) {
        this.id = bookmark.getId();
        this.codePostTitle = codePost.getTitle();
        this.commentCount = codePost.getCodePostCommentList().size();
        this.writer = member.getNickname();
        this.updateAt = bookmark.getUpdateDate();
    }
}
