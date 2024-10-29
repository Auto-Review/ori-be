package org.example.autoreview.domain.review.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewUpdateRequestDto {

    private final String description;
    private final String code;

    @Builder
    public ReviewUpdateRequestDto(String description, String code) {
        this.code = code;
        this.description = description;
    }
}
