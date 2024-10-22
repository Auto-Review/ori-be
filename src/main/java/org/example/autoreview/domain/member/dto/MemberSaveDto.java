package org.example.autoreview.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.autoreview.domain.member.entity.Member;

@Getter
@AllArgsConstructor
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
