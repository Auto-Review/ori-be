package org.example.autoreview.domain.review.dto.request;

import lombok.Getter;

@Getter
public class ReviewDeleteRequestDto {

    private final Long id;
    private final String email;

    public ReviewDeleteRequestDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
