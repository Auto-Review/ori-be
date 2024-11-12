package org.example.autoreview.domain.review.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.review.entity.Review;

@Getter
public class ReviewResponseDto {

    private final Long id;
    private final String description;
    private final String code;

    public ReviewResponseDto(Review entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.code = entity.getCode();
    }

}
