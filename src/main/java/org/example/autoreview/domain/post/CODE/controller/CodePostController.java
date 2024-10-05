package org.example.autoreview.domain.post.CODE.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.post.CODE.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.post.CODE.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.post.CODE.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.post.CODE.service.CodePostService;
import org.example.autoreview.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "코드 포스트 API", description = "코드 포스트 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
public class CodePostController {

    private final CodePostService codePostService;

    @Operation(summary = "코드 포스트 생성", description = "코드 포스트 생성")
    @PostMapping("/api/v1/post/save")
    public ApiResponse<Long> save(@RequestBody CodePostSaveRequestDto requestDto){
        return ApiResponse.success(HttpStatus.OK, codePostService.save(requestDto));
    }

    @Operation(summary = "코드 포스트 단일 조회", description = "코드 포스트 단일 조회")
    @GetMapping("/api/v1/post/code/view")
    public ApiResponse<CodePostResponseDto> view(@RequestParam Long codePostId){
        return ApiResponse.success(HttpStatus.OK, codePostService.findById(codePostId));
    }

    @Operation(summary = "코드 포스트 전체 조회", description = "코드 포스트 전체 조회")
    @GetMapping("/api/v1/post/code/view-all")
    public ApiResponse<List<CodePostResponseDto>> viewAll(){
        return ApiResponse.success(HttpStatus.OK, codePostService.findAll());
    }

    @Operation(summary = "코드 포스트 수정", description = "코드 포스트 수정")
    @PutMapping("/api/v1/post/code/update")
    public ApiResponse<Long> update(@RequestBody CodePostUpdateRequestDto requestDto) {
        return ApiResponse.success(HttpStatus.OK, codePostService.update(requestDto));
    }

    @Operation(summary = "코드 포스트 삭제", description = "코드 포스트 삭제")
    @DeleteMapping("/api/v1/post/code/delete")
    public ApiResponse<Long> delete(@RequestParam Long codePostId){
        return ApiResponse.success(HttpStatus.OK, codePostService.delete(codePostId));
    }
}
