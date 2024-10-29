package org.example.autoreview.domain.review.dto.request;

import lombok.Getter;
import org.example.autoreview.domain.review.entity.Review;

@Getter
public record ReviewSaveRequestDto(
        Long codePostId,
        String code,
        String description
) {
    public Review toEntity() {
        return Review.builder()
                .code(code)
                .description(description)
                .build();
    }
}
