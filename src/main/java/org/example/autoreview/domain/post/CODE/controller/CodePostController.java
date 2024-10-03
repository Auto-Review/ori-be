package org.example.autoreview.domain.post.CODE.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.post.CODE.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.post.CODE.service.CodePostService;
import org.example.autoreview.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "코드 포스트 API", description = "코드 포스트 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
public class CodePostController {

    private final CodePostService codePostService;

    @GetMapping("/api/v1/post/code/view")
    public ApiResponse<CodePostResponseDto> view(@RequestParam Long codePostId){
        return ApiResponse.success(HttpStatus.OK, codePostService.findById(codePostId));
    }
}
