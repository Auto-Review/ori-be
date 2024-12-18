package org.example.autoreview.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.notification.dto.request.NotificationRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.service.NotificationDtoService;
import org.example.autoreview.global.scheduler.NotificationScheduler;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "알림 API", description = "알림 API")
@RequestMapping("/v1/api/notification")
@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationScheduler notificationScheduler;
    private final NotificationDtoService notificationDtoService;

    @Operation(summary = "알림 저장 또는 수정", description = "회원 정보는 헤더에서")
    @PostMapping
    public ApiResponse<String> saveOrUpdate(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody NotificationRequestDto requestDto) {
        notificationDtoService.saveOrUpdate(userDetails.getUsername(), requestDto);
        return ApiResponse.success(HttpStatus.OK,"save or update success");
    }

    @Operation(summary = "알림 삭제", description = "알림 상태를 완료로 수정한 뒤 스케줄러로 한 번에 삭제")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable("id") Long id) {
        notificationDtoService.delete(userDetails.getUsername(), id);
        return ApiResponse.success(HttpStatus.OK,"delete success");
    }

    @Operation(summary = "알림 전체 조회", description = "회원 정보는 헤더에서")
    @GetMapping("/list")
    public ApiResponse<List<NotificationResponseDto>> findAll() {
        return ApiResponse.success(HttpStatus.OK,notificationDtoService.findAll());
    }

    @Operation(summary = "회원 알림 전체 조회", description = "회원 정보는 헤더에서")
    @GetMapping("/own")
    public ApiResponse<List<NotificationResponseDto>> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(HttpStatus.OK,notificationDtoService.findAllByMemberId(userDetails.getUsername()));
    }

    @Operation(summary = "회원 안읽은 알림 전체 조회", description = "회원이 안읽은 알림을 조회한다.")
    @GetMapping("/own/unchecked")
    public ApiResponse<List<NotificationResponseDto>> findAllNotificationIsNotCheckedByMemberId(@AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(HttpStatus.OK,notificationDtoService.findAllNotificationIsNotCheckedByMemberId(userDetails.getUsername()));
    }

    @Operation(summary = "알림 상태 변경", description = "알림을 봤다고 상태를 변경한다.")
    @PutMapping
    public ApiResponse<String> update(@RequestParam Long id) {
        notificationDtoService.update(id);
        return ApiResponse.success(HttpStatus.OK,"update success");
    }

    @Operation(summary = "푸쉬 알림 강제 시작")
    @PutMapping("/push")
    public ApiResponse<String> push() {
        notificationScheduler.pushNotification();
        return ApiResponse.success(HttpStatus.OK, "push notification complete");
    }

    @Operation(summary = "전송 완료된 푸쉬 알림 강제 삭제")
    @DeleteMapping
    public ApiResponse<String> delete() {
        notificationScheduler.deleteCompleteNotification();
        return ApiResponse.success(HttpStatus.OK, "delete notification complete");
    }
}
