package org.example.autoreview.domain.post.CODE.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.post.CODE.dto.request.CodePostSaveRequestDto;
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

    @PostMapping("/api/v1/post/save")
    public ApiResponse<Long> save(@RequestBody CodePostSaveRequestDto requestDto){
        return ApiResponse.success(HttpStatus.OK, codePostService.save(requestDto));
    }

    @GetMapping("/api/v1/post/code/view")
    public ApiResponse<CodePostResponseDto> view(@RequestParam Long codePostId){
        return ApiResponse.success(HttpStatus.OK, codePostService.findById(codePostId));
    }

    @GetMapping("/api/v1/post/code/view-all")
    public ApiResponse<List<CodePostResponseDto>> viewAll(){
        return ApiResponse.success(HttpStatus.OK, codePostService.findAll());
    }

    @DeleteMapping("/api/v1/post/code/delete")
    public ApiResponse<Long> delete(@RequestParam Long codePostId){
        return ApiResponse.success(HttpStatus.OK, codePostService.delete(codePostId));
    }

}
