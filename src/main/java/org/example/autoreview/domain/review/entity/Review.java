package org.example.autoreview.domain.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.review.dto.request.ReviewUpdateRequestDto;
import org.example.autoreview.global.common.basetime.BaseEntity;


@Getter
@NoArgsConstructor
@Entity
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private CodePost codePost;

    @Column(length = 2000)
    private String description;

    @Column(length = 1000)
    private String code;

    @Builder
    public Review(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public void setCodePost(CodePost codePost) {
        if (this.codePost != null) {
            this.codePost.getReviewList().remove(this);
        }
        this.codePost = codePost;
        codePost.getReviewList().add(this);
    }

    public void update(ReviewUpdateRequestDto requestDto) {
        this.description = requestDto.getDescription();
        this.code = requestDto.getCode();
    }

}
