package org.example.autoreview.domain.fcm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.fcm.dto.request.FcmTokenSaveRequestDto;
import org.example.autoreview.domain.fcm.service.FcmTokenMemberService;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FCM API")
@RequiredArgsConstructor
@RequestMapping("/v1/api/fcm")
@RestController
public class FcmTokenController {

    private final FcmTokenMemberService fcmTokenMemberService;

    @Operation(summary = "fcm 저장 API", description = "권한 요청 후 FCM에서 받아온 토큰 저장")
    @PostMapping("/save")
    public ApiResponse<Long> save(@RequestBody FcmTokenSaveRequestDto requestDto,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(HttpStatus.OK, fcmTokenMemberService.save(requestDto, userDetails.getUsername()));
    }
}
