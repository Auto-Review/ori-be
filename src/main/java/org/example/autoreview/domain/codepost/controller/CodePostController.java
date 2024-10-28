package org.example.autoreview.domain.codepost.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.service.CodePostService;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "코드 포스트 API", description = "코드 포스트 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post/code")
public class CodePostController {

    private final CodePostService codePostService;

    @Operation(summary = "코드 포스트 생성", description = "코드 포스트 생성")
    @PostMapping("/save")
    public ApiResponse<Long> save(@RequestBody CodePostSaveRequestDto requestDto){
        return ApiResponse.success(HttpStatus.OK, codePostService.save(requestDto));
    }

    @Operation(summary = "코드 포스트 단일 조회", description = "코드 포스트 단일 조회")
    @GetMapping("/view")
    public ApiResponse<CodePostResponseDto> view(@RequestParam Long codePostId){
        return ApiResponse.success(HttpStatus.OK, codePostService.findById(codePostId));
    }

    @Operation(summary = "코드 포스트 전체 조회", description = "코드 포스트 전체 조회")
    @GetMapping("/view-all")
    public ApiResponse<List<CodePostResponseDto>> viewAll(){
        return ApiResponse.success(HttpStatus.OK, codePostService.findAll());
    }

    @Operation(summary = "코드 포스트 수정", description = "코드 포스트 수정")
    @PutMapping("/update")
    public ApiResponse<Long> update(@RequestBody CodePostUpdateRequestDto requestDto) {
        return ApiResponse.success(HttpStatus.OK, codePostService.update(requestDto));
    }

    @Operation(summary = "코드 포스트 삭제", description = "코드 포스트 삭제")
    @DeleteMapping("/delete")
    public ApiResponse<Long> delete(@RequestBody Long codePostId){
        return ApiResponse.success(HttpStatus.OK, codePostService.delete(codePostId));
    }
}
