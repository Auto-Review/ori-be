package org.example.autoreview.dto;

import lombok.Data;
import lombok.Getter;
import org.example.autoreview.domain.Member;

@Getter
public class MemberResponseDto {
    private Long id;
    private String email;
    private String nickname;

    public MemberResponseDto(Member entity){
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
    }
}
