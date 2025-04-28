package org.example.autoreview.domain.review.dto.response;

import lombok.Getter;
import org.example.autoreview.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {

    private final Long id;
    private final String description;
    private final String code;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ReviewResponseDto(Review entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.code = entity.getCode();
        this.createdAt = entity.getCreateDate();
        this.updatedAt = entity.getUpdateDate();
    }

}
