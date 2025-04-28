package org.example.autoreview.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.review.dto.request.ReviewDeleteRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewSaveRequestDto;
import org.example.autoreview.domain.review.dto.request.ReviewUpdateRequestDto;
import org.example.autoreview.domain.review.dto.response.ReviewResponseDto;
import org.example.autoreview.domain.review.service.ReviewCodePostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "리뷰 API")
@RequiredArgsConstructor
@RequestMapping("/v1/api/review")
@RestController
public class ReviewController {

    private final ReviewCodePostService reviewCodePostService;

    @Operation(summary = "리뷰 저장")
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody ReviewSaveRequestDto requestDto) {
        return ResponseEntity.ok().body(reviewCodePostService.save(requestDto));
    }

    @Operation(summary = "리뷰 단일 조회")
    @GetMapping("/detail/{id}")
    public ResponseEntity<ReviewResponseDto> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(reviewCodePostService.findOne(id));
    }

    @Operation(summary = "리뷰 전체 조회", description = "코드 포스트에 해당하는 리뷰만 볼 수 있음")
    @GetMapping("/{codePostId}/list")
    public ResponseEntity<List<ReviewResponseDto>> findAll(@PathVariable("codePostId") Long codePostId) {
        return ResponseEntity.ok().body(reviewCodePostService.findAllByCodePostId(codePostId));
    }

    @Operation(summary = "리뷰 수정", description = "사용자 검사 후 수정")
    @PutMapping
    public ResponseEntity<Long> update(@RequestBody ReviewUpdateRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(reviewCodePostService.update(requestDto, userDetails.getUsername()));
    }

    @Operation(summary = "리뷰 삭제", description = "사용자 검사 후 삭제")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody ReviewDeleteRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        reviewCodePostService.delete(requestDto, userDetails.getUsername());
        return ResponseEntity.ok().body("Review deleted");
    }
}
