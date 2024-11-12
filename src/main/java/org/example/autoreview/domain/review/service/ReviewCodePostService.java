package org.example.autoreview.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.service.CodePostService;
import org.example.autoreview.domain.review.dto.request.ReviewDeleteRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewSaveRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewUpdateRequestDto;
import org.example.autoreview.domain.review.dto.response.ReviewResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewCodePostService {

    private final ReviewService reviewService;
    private final CodePostService codePostService;

    public void save(ReviewSaveRequestDto requestDto) {
        CodePost codePost = codePostService.findEntityById(requestDto.codePostId());
        reviewService.save(requestDto, codePost);
    }

    public ReviewResponseDto findOne(Long reviewId) {
        return reviewService.findOne(reviewId);
    }

    public List<ReviewResponseDto> findAll() {
        return reviewService.findAll();
    }

    public void update(ReviewUpdateRequestDto requestDto, String email) {
        reviewService.update(requestDto, email);
    }

    public void delete(ReviewDeleteRequestDto requestDto, String email) {
        reviewService.delete(requestDto, email);
    }

}
