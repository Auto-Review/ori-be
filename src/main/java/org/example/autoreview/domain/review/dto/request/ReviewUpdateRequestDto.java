package org.example.autoreview.domain.review.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewUpdateRequestDto {

    private final Long id;
    private final String email;
    private final String description;
    private final String code;

    @Builder
    public ReviewUpdateRequestDto(Long id, String email, String description, String code) {
        this.id = id;
        this.email = email;
        this.code = code;
        this.description = description;
    }
}
