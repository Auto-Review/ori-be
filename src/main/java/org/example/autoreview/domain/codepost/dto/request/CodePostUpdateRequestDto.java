package org.example.autoreview.domain.codepost.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CodePostUpdateRequestDto {

    @Schema(description = "코드 포스트 PK 값", example = "1")
    private Long id;

    @Schema(description = "코드 포스트 제목", example = "[BOJ] 0000: test 해보기")
    private String title;

    @Schema(description = "해설", example = "test 기법을 사용해서 구현")
    private String description;

    @Schema(description = "난이도", example = "4.5")
    private int level;

    @Schema(description = "복습일 설정", example = "2024-10-11")
    private LocalDateTime reviewTime;

    @Schema(description = "코드", example = "import test")
    private String code;
}
