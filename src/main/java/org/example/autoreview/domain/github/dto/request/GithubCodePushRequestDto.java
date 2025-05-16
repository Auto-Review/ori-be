package org.example.autoreview.domain.github.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record GithubCodePushRequestDto(

        @Schema(description = "코드 포스트 제목", example = "[BOJ] 0000: test 해보기")
        String title,

        @Schema(description = "난이도", example = "4")
        int level,

        @Schema(description = "해설", example = "test 기법을 사용해서 구현")
        String description,

        @NotNull
        @Schema(description = "사용 언어", example = "java")
        String language,

        @Schema(description = "코드", example = "import test")
        String code
) {
}
