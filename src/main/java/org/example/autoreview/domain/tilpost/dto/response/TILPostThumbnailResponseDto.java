package org.example.autoreview.domain.tilpost.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.tilpost.entity.TILPost;

@Getter
public class TILPostThumbnailResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final MemberResponseDto member;
    private final LocalDateTime createdDate;

    public TILPostThumbnailResponseDto(TILPost entity, String content){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = content;
        this.member = new MemberResponseDto(entity.getMember());
        this.createdDate = entity.getCreateDate();
    }
}
