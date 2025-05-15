package org.example.autoreview.domain.github.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.autoreview.domain.github.entity.GithubToken;

public record GithubTokenRequestDto(

        @Schema(description = "인증 코드")
        String githubToken,

        @Schema(description = "사용자 이메일", example = "abc@gmail.com")
        String email
) {
    public GithubToken toEntity(){
        return GithubToken.builder()
                .githubToken(githubToken)
                .email(email)
                .build();
    }
}
