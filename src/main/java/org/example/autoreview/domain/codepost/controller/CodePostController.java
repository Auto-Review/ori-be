package org.example.autoreview.domain.codepost.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostListResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.service.CodePostService;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "코드 포스트 API", description = "코드 포스트 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/post/code")
public class CodePostController {

    private final CodePostService codePostService;

    @Operation(summary = "코드 포스트 생성", description = "코드 포스트 생성")
    @PostMapping("/save")
    public ApiResponse<Long> save(@RequestBody CodePostSaveRequestDto requestDto,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(HttpStatus.OK, codePostService.save(requestDto, userDetails.getUsername()));
    }

    @Operation(summary = "제목으로 코드 포스트 검색", description = "공백 또는 null 입력 시 에러 반환")
    @GetMapping("/search")
    public ApiResponse<CodePostListResponseDto> search(@RequestParam String keyword,
                                                       @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ApiResponse.success(HttpStatus.OK, codePostService.search(keyword, pageable));
    }

    @Operation(summary = "코드 포스트 단일 조회", description = "코드 포스트 단일 조회")
    @GetMapping("/view/{id}")
    public ApiResponse<CodePostResponseDto> view(@PathVariable("id") Long codePostId) {
        return ApiResponse.success(HttpStatus.OK, codePostService.findById(codePostId));
    }

    @Operation(summary = "코드 포스트 전체 조회", description = "코드 포스트 전체 조회")
    @GetMapping("/view-all")
    public ApiResponse<CodePostListResponseDto> viewAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ApiResponse.success(HttpStatus.OK, codePostService.findByPage(pageable));
    }

    @Operation(summary = "코드 포스트 수정", description = "코드 포스트 수정")
    @PutMapping("/update")
    public ApiResponse<Long> update(@RequestBody CodePostUpdateRequestDto requestDto,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(HttpStatus.OK, codePostService.update(requestDto, userDetails.getUsername()));
    }

    @Operation(summary = "코드 포스트 삭제", description = "코드 포스트 삭제")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Long> delete(@PathVariable("id") Long codePostId,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(HttpStatus.OK, codePostService.delete(codePostId, userDetails.getUsername()));
    }
}
