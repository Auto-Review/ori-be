package org.example.autoreview.domain.tilpost.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.tilpost.dto.request.TILPostSaveRequestDto;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostListResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostResponseDto;
import org.example.autoreview.domain.tilpost.service.TILPostService;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/api/til")
@RestController
public class TILPostApiController {

    private final TILPostService tilPostService;

    @Operation(summary = "TIL 게시물 전체 조회", description = "전체 조회")
    @GetMapping
    public ApiResponse<List<TILPostListResponseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(HttpStatus.OK, tilPostService.findAll(pageable));
    }

    @Operation(summary = "특정 TIL 게시물 조회", description = "개별 조회")
    @GetMapping("/{id}")
    public ApiResponse<TILPostResponseDto> findById(@PathVariable Long id){
        return ApiResponse.success(HttpStatus.OK, tilPostService.findById(id));
    }

    @Operation(summary = "TIL 게시물 생성", description = "토큰을 통해 유저 선택")
    @PostMapping("/save")
    public ApiResponse<Long> save(@RequestBody TILPostSaveRequestDto saveRequestDto,
                                  @AuthenticationPrincipal UserDetails userDetails){

        return ApiResponse.success(HttpStatus.OK,
                tilPostService.save(saveRequestDto, userDetails.getUsername()));
    }

    @PutMapping("/update")
    public ApiResponse<Long> update(@RequestBody TILPostUpdateRequestDto requestDto){

        return ApiResponse.success(HttpStatus.OK, tilPostService.update(requestDto));
    }

    @DeleteMapping("/delete")
    public ApiResponse<Long> delete(@RequestBody Long id,
                                    @AuthenticationPrincipal UserDetails userDetails){

        return ApiResponse.success(HttpStatus.OK, tilPostService.delete(id, userDetails.getUsername()));
    }
}
