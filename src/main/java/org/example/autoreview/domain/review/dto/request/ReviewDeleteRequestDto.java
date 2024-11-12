package org.example.autoreview.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewDeleteRequestDto {

    @Schema(description = "리뷰 아이디", example = "1")
    private final Long id;

    @Schema(description = "현재 포스트에 저장된 email", example = "abc@naver.com")
    private final String email;

    public ReviewDeleteRequestDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
