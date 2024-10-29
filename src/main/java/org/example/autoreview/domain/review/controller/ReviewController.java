package org.example.autoreview.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.review.dto.request.ReviewSaveRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewUpdateRequestDto;
import org.example.autoreview.domain.review.entity.Review;
import org.example.autoreview.domain.review.service.ReviewService;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/api/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/save")
    public ApiResponse<String> save(ReviewSaveRequestDto requestDto) {
        reviewService.save(requestDto);
        return ApiResponse.success(HttpStatus.OK, "Review saved");
    }

    @GetMapping("/find-one")
    public ApiResponse<Review> findOne(Long id) {
        return ApiResponse.success(HttpStatus.OK, reviewService.findOne(id));
    }

    @GetMapping("/find-all")
    public ApiResponse<List<Review>> findAll() {
        return ApiResponse.success(HttpStatus.OK, reviewService.findAll());
    }

    @PutMapping("/update")
    public ApiResponse<String> update(ReviewUpdateRequestDto requestDto) {
        reviewService.update(requestDto);
        return ApiResponse.success(HttpStatus.OK, "Review updated");
    }

    @DeleteMapping("/delete")
    public ApiResponse<String> delete(Long id) {
        reviewService.delete(id);
        return ApiResponse.success(HttpStatus.OK, "Review deleted");
    }
}
