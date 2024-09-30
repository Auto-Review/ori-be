package org.example.autoreview.domain.member.dto;

import lombok.Getter;
import org.example.autoreview.domain.member.entity.Member;

@Getter
public class MemberSaveDto {

    private String email;
    private String nickname;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
}
