package org.example.autoreview.domain.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.service.CodePostService;
import org.example.autoreview.domain.review.dto.request.ReviewDeleteRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewSaveRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewUpdateRequestDto;
import org.example.autoreview.domain.review.dto.response.ReviewResponseDto;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewCodePostService {

    private final ReviewService reviewService;
    private final CodePostService codePostService;

    public Long save(ReviewSaveRequestDto requestDto) {
        CodePost codePost = codePostService.findEntityById(requestDto.codePostId());
        return reviewService.save(requestDto, codePost).getId();
    }

    public ReviewResponseDto findOne(Long reviewId) {
        return reviewService.findOne(reviewId);
    }

    public List<ReviewResponseDto> findAllByCodePostId(Long codePostId) {
        CodePost codePost = codePostService.findEntityById(codePostId);
        return reviewService.findAllByCodePost(codePost);
    }

    public Long update(ReviewUpdateRequestDto requestDto, String email) {
        return reviewService.update(requestDto, email).getId();
    }

    public void delete(ReviewDeleteRequestDto requestDto, String email) {
        reviewService.delete(requestDto, email);
    }

}
