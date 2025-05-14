package org.example.autoreview.domain.github.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record GithubCodeRequestDto(

        @Schema(description = "리다이렉트로 온 일회성 코드")
        String code
) {
}
