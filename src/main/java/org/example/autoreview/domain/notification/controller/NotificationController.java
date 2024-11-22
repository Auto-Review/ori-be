package org.example.autoreview.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.notification.dto.request.NotificationSaveRequestDto;
import org.example.autoreview.domain.notification.dto.request.NotificationUpdateRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.service.NotificationDtoService;
import org.example.autoreview.domain.scheduler.NotificationScheduler;
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

    private final NotificationDtoService notificationMemberService;
    private final NotificationScheduler notificationScheduler;
    private final NotificationDtoService notificationDtoService;

    @Operation(summary = "알림 저장", description = "회원 정보는 헤더에서")
    @PostMapping
    public ApiResponse<String> save(@RequestParam Long codePostId,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    @RequestBody NotificationSaveRequestDto requestDto) {

        notificationMemberService.save(userDetails.getUsername(), codePostId, requestDto);
        return ApiResponse.success(HttpStatus.OK,"save success");
    }

    @Operation(summary = "알림 수정", description = "날짜 수정만 가능")
    @PutMapping
    public ApiResponse<String> update(@AuthenticationPrincipal UserDetails userDetails,
                                      @RequestBody NotificationUpdateRequestDto requestDto) {
        notificationMemberService.update(userDetails.getUsername(), requestDto);
        return ApiResponse.success(HttpStatus.OK,"update success");
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
        return ApiResponse.success(HttpStatus.OK,notificationMemberService.findAll());
    }

    @Operation(summary = "회원 알림 전체 조회", description = "회원 정보는 헤더에서")
    @GetMapping("/own")
    public ApiResponse<List<NotificationResponseDto>> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(HttpStatus.OK,notificationMemberService.findAllByMemberId(userDetails.getUsername()));
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
