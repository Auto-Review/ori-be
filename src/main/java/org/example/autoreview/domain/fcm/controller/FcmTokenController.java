package org.example.autoreview.domain.fcm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.fcm.dto.request.FcmTokenRequestDto;
import org.example.autoreview.domain.fcm.service.FcmTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FCM API")
@RequiredArgsConstructor
@RequestMapping("/v1/api/fcm")
@RestController
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @Operation(summary = "fcm 저장 API", description = "권한 요청 후 FCM에서 받아온 토큰 저장")
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody FcmTokenRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(fcmTokenService.save(requestDto, userDetails.getUsername()));
    }

    @Operation(summary = "fcm 삭제 API", description = "FCM 삭제")
    @DeleteMapping
    public ResponseEntity<Long> delete(@RequestBody FcmTokenRequestDto fcmTokenRequestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(fcmTokenService.delete(fcmTokenRequestDto, userDetails.getUsername()));
    }
}
