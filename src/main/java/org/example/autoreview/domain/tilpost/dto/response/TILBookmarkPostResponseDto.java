package org.example.autoreview.domain.tilpost.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.autoreview.domain.tilpost.entity.TILPost;

@Getter
public class TILBookmarkPostResponseDto {
    private final Long id;
    private final Long writerId;
    private final String writerEmail;
    private final String writerNickName;
    private final String title;
    private final String content;
    private final LocalDateTime createDate;
    private final Boolean isBookmarked;

    public TILBookmarkPostResponseDto(TILPost entity, Boolean isBookmarked){
        this.id = entity.getId();
        this.writerId = entity.getWriterId();
        this.writerEmail = entity.getWriterEmail();
        this.writerNickName = getWriterNickName();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
        this.isBookmarked = isBookmarked;
    }
}
