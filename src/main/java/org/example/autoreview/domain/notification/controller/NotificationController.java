package org.example.autoreview.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.notification.dto.request.NotificationSaveRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.service.NotificationService;
import org.example.autoreview.domain.scheduler.NotificationScheduler;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "알림 API", description = "알림 API")
@RequestMapping("/v1/api/notification")
@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationScheduler notificationScheduler;

    @Operation(summary = "알림 저장", description = "회원 정보는 헤더에서")
    @PostMapping("/save")
    public ApiResponse<String> save(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestBody NotificationSaveRequestDto requestDto) {

        notificationService.save(userDetails.getUsername(), requestDto);
        return ApiResponse.success(HttpStatus.OK,"save success");
    }

    @Operation(summary = "알림 전체 조회", description = "회원 정보는 헤더에서")
    @GetMapping("/find-all")
    public ApiResponse<List<NotificationResponseDto>> findAll() {
        return ApiResponse.success(HttpStatus.OK,notificationService.findAll());
    }

    @Operation(summary = "회원 알림 전체 조회", description = "회원 정보는 헤더에서")
    @GetMapping("/{email}/find-all")
    public ApiResponse<List<NotificationResponseDto>> findAll(@PathVariable String email) {
        return ApiResponse.success(HttpStatus.OK,notificationService.findAllByEmail(email));
    }


    @Operation(summary = "푸쉬 알림 강제 시작")
    @PutMapping("/push")
    public ApiResponse<String> push() {
        notificationScheduler.pushNotification();
        return ApiResponse.success(HttpStatus.OK, "push notification complete");
    }
}
