package org.example.autoreview.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "리뷰 저장")
    @PostMapping("/save")
    public ApiResponse<String> save(ReviewSaveRequestDto requestDto) {
        reviewCodePostService.save(requestDto);
        return ApiResponse.success(HttpStatus.OK, "Review saved");
    }

    @Operation(summary = "사용자 단일 조회")
    @GetMapping("/find-one")
    public ApiResponse<ReviewResponseDto> findOne(Long id) {
        return ApiResponse.success(HttpStatus.OK, reviewCodePostService.findOne(id));
    }

    @Operation(summary = "리뷰 전체 조회")
    @GetMapping("/find-all")
    public ApiResponse<List<ReviewResponseDto>> findAll() {
        return ApiResponse.success(HttpStatus.OK, reviewCodePostService.findAll());
    }

    @Operation(summary = "리뷰 수정", description = "사용자 검사 후 수정")
    @PutMapping("/update")
    public ApiResponse<String> update(ReviewUpdateRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        reviewCodePostService.update(requestDto, userDetails.getUsername());
        return ApiResponse.success(HttpStatus.OK, "Review updated");
    }

    @Operation(summary = "리뷰 삭제", description = "사용자 검사 후 삭제")
    @DeleteMapping("/delete")
    public ApiResponse<String> delete(ReviewDeleteRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        reviewCodePostService.delete(requestDto, userDetails.getUsername());
        return ApiResponse.success(HttpStatus.OK, "Review deleted");
    }
}
