package org.example.autoreview.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewUpdateRequestDto {

    @Schema(description = "리뷰 아이디", example = "1")
    private final Long id;

    @Schema(description = "사용자 검증을 위한 이메일 정보", example = "abc@naver.com")
    private final String email;

    @Schema(description = "복습 코드에 대한 해설", example = "링가르디움 레비오사")
    private final String description;

    @Schema(description = "복습 코드", example = "HTML도 컴퓨터 언어입니다..")
    private final String code;

    @Builder
    public ReviewUpdateRequestDto(Long id, String email, String description, String code) {
        this.id = id;
        this.email = email;
        this.code = code;
        this.description = description;
    }
}
