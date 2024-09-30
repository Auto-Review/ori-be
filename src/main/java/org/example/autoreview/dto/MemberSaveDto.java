package org.example.autoreview.dto;

import lombok.Data;
import lombok.Getter;
import org.example.autoreview.domain.Member;

@Getter
@Data
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
