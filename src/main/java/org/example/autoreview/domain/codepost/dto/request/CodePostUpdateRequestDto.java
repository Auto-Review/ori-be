package org.example.autoreview.domain.codepost.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CodePostUpdateRequestDto {

    @Schema(description = "코드 포스트 PK 값", example = "1")
    private final Long id;

    @Schema(description = "코드 포스트 제목", example = "[BOJ] 0000: test 해보기")
    private final String title;

    @Schema(description = "해설", example = "test 기법을 사용해서 구현")
    private final String description;

    @Schema(description = "난이도", example = "4")
    private final int level;

    @Schema(description = "공개여부", example = "true")
    private final boolean isPublic;

    @Schema(description = "복습일 설정", example = "2024-10-11")
    private final LocalDate reviewDay;

    @Schema(description = "사용 언어", example = "cpp")
    private final String language;

    @Schema(description = "코드", example = "import test")
    private final String code;

}
