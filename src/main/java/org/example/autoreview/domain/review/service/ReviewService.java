package org.example.autoreview.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.service.CodePostService;
import org.example.autoreview.domain.review.dto.request.ReviewSaveRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewUpdateRequestDto;
import org.example.autoreview.domain.review.entity.Review;
import org.example.autoreview.domain.review.entity.ReviewRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final CodePostService codePostService;

    @Transactional
    public void save(ReviewSaveRequestDto requestDto) {
        CodePost codePost = codePostService.findEntityById(requestDto.codePostId());
        Review review = requestDto.toEntity();
        review.setCodePost(codePost);
        reviewRepository.save(review);
    }

    public Review findOne(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_REVIEW));
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Transactional
    public void update(ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(requestDto.getId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_REVIEW)
        );
        review.update(requestDto);
    }

    @Transactional
    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
