package org.example.autoreview.domain.member.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JwtDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
