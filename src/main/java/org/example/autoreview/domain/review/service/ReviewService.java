package org.example.autoreview.domain.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.review.dto.request.ReviewDeleteRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewSaveRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewUpdateRequestDto;
import org.example.autoreview.domain.review.dto.response.ReviewResponseDto;
import org.example.autoreview.domain.review.entity.Review;
import org.example.autoreview.domain.review.entity.ReviewRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review save(ReviewSaveRequestDto requestDto, CodePost codePost) {
        Review review = requestDto.toEntity(codePost);
        return reviewRepository.save(review);
    }

    public ReviewResponseDto findOne(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_REVIEW));
        return new ReviewResponseDto(review);
    }

    public List<ReviewResponseDto> findAllByCodePost(CodePost codePost) {
        return codePost.getReviewList().stream()
                .map(ReviewResponseDto::new)
                .toList();
    }

    @Transactional
    public Review update(ReviewUpdateRequestDto requestDto, String email) {
        userValidator(requestDto.getEmail(), email);
        Review review = reviewRepository.findById(requestDto.getId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_REVIEW)
        );
        return review.update(requestDto);
    }

    @Transactional
    public void delete(ReviewDeleteRequestDto requestDto, String email) {
        userValidator(requestDto.getEmail(), email);
        reviewRepository.deleteById(requestDto.getId());
    }

    private static void userValidator(String dtoEmail, String email) {
        if (!dtoEmail.equals(email)) {
            throw new BadRequestException(ErrorCode.UNMATCHED_EMAIL);
        }
    }

}
