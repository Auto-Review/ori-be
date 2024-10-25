package org.example.autoreview.domain.tilpost.controller;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.tilpost.dto.request.TILPostSaveRequestDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostListResponseDto;
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

    @GetMapping
    public ApiResponse<List<TILPostListResponseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(HttpStatus.OK, tilPostService.findAll(pageable));
    }

    @PostMapping
    public ApiResponse<Long> save(@RequestBody TILPostSaveRequestDto saveRequestDto,
                                  @AuthenticationPrincipal UserDetails userDetails){

        return ApiResponse.success(HttpStatus.OK,
                tilPostService.save(saveRequestDto, userDetails.getUsername()));
    }
}
