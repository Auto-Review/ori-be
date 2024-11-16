package org.example.autoreview.domain.tilpost.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.tilpost.entity.TILPost;

import java.time.LocalDateTime;

@Getter
public class TILBookmarkPostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final MemberResponseDto member;
    private final LocalDateTime createDate;
    private final Boolean isBookmarked;

    public TILBookmarkPostResponseDto(TILPost entity, Boolean isBookmarked){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.member = new MemberResponseDto(entity.getMember());
        this.createDate = entity.getCreateDate();
        this.isBookmarked = isBookmarked;
    }
}
