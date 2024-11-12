package org.example.autoreview.domain.review.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.review.dto.request.ReviewDeleteRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewSaveRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewUpdateRequestDto;
import org.example.autoreview.domain.review.dto.response.ReviewResponseDto;
import org.example.autoreview.domain.review.service.ReviewCodePostService;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "리뷰 API")
@RequiredArgsConstructor
@RequestMapping("/v1/api/review")
@RestController
public class ReviewController {

    private final ReviewCodePostService reviewCodePostService;

    @PostMapping("/save")
    public ApiResponse<String> save(ReviewSaveRequestDto requestDto) {
        reviewCodePostService.save(requestDto);
        return ApiResponse.success(HttpStatus.OK, "Review saved");
    }

    @GetMapping("/find-one")
    public ApiResponse<ReviewResponseDto> findOne(Long id) {
        return ApiResponse.success(HttpStatus.OK, reviewCodePostService.findOne(id));
    }

    @GetMapping("/find-all")
    public ApiResponse<List<ReviewResponseDto>> findAll() {
        return ApiResponse.success(HttpStatus.OK, reviewCodePostService.findAll());
    }

    @PutMapping("/update")
    public ApiResponse<String> update(ReviewUpdateRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        reviewCodePostService.update(requestDto, userDetails.getUsername());
        return ApiResponse.success(HttpStatus.OK, "Review updated");
    }

    @DeleteMapping("/delete")
    public ApiResponse<String> delete(ReviewDeleteRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        reviewCodePostService.delete(requestDto, userDetails.getUsername());
        return ApiResponse.success(HttpStatus.OK, "Review deleted");
    }
}
