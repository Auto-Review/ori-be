package org.example.autoreview.domain.tilpost.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.tilpost.dto.request.TILPostSaveRequestDto;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostResponseDto;
import org.example.autoreview.domain.tilpost.service.TILPostDtoService;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/v1/api/post/til")
@RestController
public class TILPostApiController {

    private final TILPostDtoService tilPostDtoService;

    @Operation(summary = "TIL 게시물 전체 조회", description = "전체 조회")
    @GetMapping("/view-all")
    public ApiResponse<TILPageResponseDto> findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                                   @RequestParam(defaultValue = "10", required = false) int size){

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(HttpStatus.OK, tilPostDtoService.findPostAllByPage(pageable));
    }

    @Operation(summary = "본인 TIL 게시물 조회", description = "멤버별 조회")
    @GetMapping("/my/view-all")
    public ApiResponse<TILPageResponseDto> findByMember(@RequestParam(defaultValue = "0", required = false) int page,
                                                        @RequestParam(defaultValue = "10", required = false) int size,
                                                        @AuthenticationPrincipal UserDetails userDetails){

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(HttpStatus.OK, tilPostDtoService.findPostByMember(userDetails.getUsername(), pageable));
    }

    @Operation(summary = "본인 TIL 게시물 검색", description = "멤버별 검색")
    @GetMapping("/my/search")
    public ApiResponse<TILPageResponseDto> findByMemberTitleContains(@RequestParam(defaultValue = "0", required = false) int page,
                                                                     @RequestParam(defaultValue = "10", required = false) int size,
                                                                     @RequestParam(required = false) String keyword,
                                                                     @AuthenticationPrincipal UserDetails userDetails){

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(HttpStatus.OK, tilPostDtoService.findPostByMemberTitleContains(userDetails.getUsername(), keyword, pageable));
    }

    @Operation(summary = "TIL 게시물 검색", description = "검색")
    @GetMapping("/search")
    public ApiResponse<TILPageResponseDto> findByTitleContains(@RequestParam(defaultValue = "0", required = false) int page,
                                                               @RequestParam(defaultValue = "10", required = false) int size,
                                                               @RequestParam(required = false) String keyword){

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(HttpStatus.OK, tilPostDtoService.findPostByTitleContains(keyword, pageable));
    }

//    @GetMapping("/cursor")
//    public ApiResponse<TILCursorResponseDto> findAllByCursorId(@RequestParam(required = false) Long cursorId,
//                                                               @RequestParam int pageSize){
//
//        return ApiResponse.success(HttpStatus.OK, tilPostService.findAllByIdCursorBased(cursorId, pageSize));
//    }

    @Operation(summary = "특정 TIL 게시물 조회", description = "개별 조회")
    @GetMapping("/view/{id}")
    public ApiResponse<TILPostResponseDto> findById(@PathVariable Long id){
        return ApiResponse.success(HttpStatus.OK, tilPostDtoService.findPostById(id));
    }

    @Operation(summary = "TIL 게시물 생성", description = "토큰을 통해 유저 선택")
    @PostMapping("/save")
    public ApiResponse<Long> save(@RequestBody TILPostSaveRequestDto saveRequestDto,
                                  @AuthenticationPrincipal UserDetails userDetails){

        return ApiResponse.success(HttpStatus.OK,
                tilPostDtoService.postSave(saveRequestDto, userDetails.getUsername()));
    }

    @Operation(summary = "TIL 게시물 갱신", description = "토큰을 통해 유저 식별")
    @PutMapping("/update")
    public ApiResponse<Long> update(@RequestBody TILPostUpdateRequestDto requestDto,
                                    @AuthenticationPrincipal UserDetails userDetails){

        return ApiResponse.success(HttpStatus.OK, tilPostDtoService.postUpdate(requestDto, userDetails.getUsername()));
    }

    //TODO: validation 검사를 해야하지 않나?
    @PostMapping("/bookmark")
    public ApiResponse<?> bookmark(@RequestBody Long postId,
                                   @AuthenticationPrincipal UserDetails userDetails){
        return ApiResponse.success(HttpStatus.OK, tilPostDtoService.bookmarkPost(userDetails.getUsername(), postId));
    }

    @Operation(summary = "TIL 게시물 삭제", description = "토큰을 통해 유저 식별")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Long> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails){

        return ApiResponse.success(HttpStatus.OK, tilPostDtoService.postDelete(id, userDetails.getUsername()));
    }
}
