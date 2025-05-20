package org.example.autoreview.domain.codepost.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.Language;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;

public record CodePostSaveRequestDto(

        @Schema(description = "코드 포스트 제목", example = "[BOJ] 0000: test 해보기")
        String title,

        @Schema(description = "난이도", example = "4")
        int level,

        @Schema(description = "공개여부", example = "true")
        boolean isPublic,

        @Schema(description = "복습일 설정", example = "2024-10-11")
        LocalDate reviewDay,

        @Schema(description = "해설", example = "test 기법을 사용해서 구현")
        String description,

        @NotNull
        @Schema(description = "사용 언어", example = "java")
        String language,

        @Schema(description = "코드", example = "import test")
        String code
) {
    public CodePost toEntity(Member member){
        return CodePost.builder()
                .writerId(member.getId())
                .title(title)
                .level(level)
                .isPublic(isPublic)
                .reviewDay(reviewDay)
                .description(description)
                .language(Language.of(language).orElseThrow(
                        () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_LANGUAGE)
                ))
                .code(code)
                .build();
    }
}
