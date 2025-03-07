package org.example.autoreview.domain.tilpost.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.autoreview.domain.tilpost.entity.TILPost;

@Getter
public class TILPostResponseDto {

    private final Long id;
    private final Long writerId;
    private final String writerEmail;
    private final String writerNickName;
    private final String title;
    private final String content;
    private final LocalDateTime createDate;

    public TILPostResponseDto(TILPost entity){
        this.id = entity.getId();
        this.writerId = entity.getWriterId();
        this.writerEmail = entity.getWriterEmail();
        this.writerNickName = entity.getWriterNickName();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
    }
}
