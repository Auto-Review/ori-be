package org.example.autoreview.domain.member.sociallogin;

import lombok.Builder;
import lombok.Getter;
import org.example.autoreview.global.jwt.JwtDto;

@Getter
public class LoginDto {
    private JwtDto jwt;
    private String email;

    @Builder
    public LoginDto(JwtDto jwt, String email){
        this.jwt = jwt;
        this.email = email;
    }
}
