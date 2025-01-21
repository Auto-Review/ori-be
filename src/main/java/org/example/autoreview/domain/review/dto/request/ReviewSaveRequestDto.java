package org.example.autoreview.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.review.entity.Review;

public record ReviewSaveRequestDto(
        @Schema(description = "리뷰 아이디", example = "1")
        Long codePostId,

        @Schema(description = "복습 코드", example = "import java.x")
        String code,

        @Schema(description = "코드에 대한 해설", example = "java.x 라이브러리를 불러오는 코드이다.")
        String description
) {
    public Review toEntity(CodePost codePost) {
        return Review.builder()
                .codePost(codePost)
                .code(code)
                .description(description)
                .build();
    }
}
