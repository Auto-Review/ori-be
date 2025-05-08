package org.example.autoreview.domain.codepost.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;

@Getter
public class MyCodePostThumbnailResponseDto {

    private final Long id;
    private final Long writerId;
    private final String writerEmail;
    private final String writerNickName;
    private final String title;
    private final int level;
    private final int commentCount;
    private final int reviewCount;
    private final LocalDateTime createdDate;

    public MyCodePostThumbnailResponseDto(CodePost entity, Member writer) {
        this.id = entity.getId();
        this.writerId = entity.getWriterId();
        this.writerEmail = writer.getEmail();
        this.writerNickName = writer.getNickname();
        this.title = entity.getTitle();
        this.level = entity.getLevel();
        this.commentCount = entity.getCodePostCommentList().size();
        this.reviewCount = entity.getReviewList().size();
        this.createdDate = entity.getCreateDate();
    }
}
