package org.example.autoreview.common.jwt;

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
