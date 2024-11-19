package org.example.autoreview.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.service.MyPageService;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api/mypage")
@RequiredArgsConstructor
@RestController
public class MemberPageController {

    private final MyPageService myPageService;

    @GetMapping("/info")
    public ApiResponse<MemberResponseDto> info(@AuthenticationPrincipal UserDetails userDetails){
        return ApiResponse.success(HttpStatus.OK, myPageService.memberInfo(userDetails.getUsername()));
    }

    @GetMapping("/bookmark/til")
    public ApiResponse<TILPageResponseDto> bookmarkedTIL(@PageableDefault(page = 0, size = 9) Pageable pageable,
                                                         @AuthenticationPrincipal UserDetails userDetails){
        return ApiResponse.success(HttpStatus.OK, myPageService.memberBookmarkedTILPost(userDetails.getUsername(), pageable));
    }
}
