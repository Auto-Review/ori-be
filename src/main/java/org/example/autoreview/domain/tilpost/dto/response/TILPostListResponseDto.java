package org.example.autoreview.domain.tilpost.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.tilpost.entity.TILPost;

import java.time.LocalDateTime;

@Getter
public class TILPostListResponseDto {
    private Long id;
    private String title;
    private String content;
    private MemberResponseDto member;
    private LocalDateTime createdDate;

    @Builder
    public TILPostListResponseDto(TILPost entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.member = new MemberResponseDto(entity.getMember());
        this.createdDate = entity.getCreateDate();
    }
}
