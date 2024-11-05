package org.example.autoreview.domain.codepost.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;

import java.time.LocalDateTime;

@Builder
public record CodePostSaveRequestDto(

        @Schema(description = "코드 포스트 제목", example = "[BOJ] 0000: test 해보기")
        String title,

        @Schema(description = "난이도", example = "4.5")
        int level,

        @Schema(description = "복습일 설정", example = "2024-10-11")
        LocalDateTime reviewTime,

        @Schema(description = "해설", example = "test 기법을 사용해서 구현")
        String description,

        @Schema(description = "코드", example = "import test")
        String code
) {
    public CodePost toEntity(Member member){
        return CodePost.builder()
                .title(title)
                .member(member)
                .level(level)
                .reviewTime(reviewTime)
                .description(description)
                .code(code)
                .build();
    }
}
