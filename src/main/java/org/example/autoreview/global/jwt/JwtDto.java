package org.example.autoreview.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}
