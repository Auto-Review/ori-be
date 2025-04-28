package org.example.autoreview.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.Role;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveDto {

    private String email;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .nickname("user")
                .role(Role.USER)
                .build();
    }
}
