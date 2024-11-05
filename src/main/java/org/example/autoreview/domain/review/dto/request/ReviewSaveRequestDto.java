package org.example.autoreview.domain.review.dto.request;

import org.example.autoreview.domain.review.entity.Review;

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
