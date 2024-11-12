package org.example.autoreview.domain.review.dto.request;

import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.review.entity.Review;

public record ReviewSaveRequestDto(
        Long codePostId,
        String code,
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
