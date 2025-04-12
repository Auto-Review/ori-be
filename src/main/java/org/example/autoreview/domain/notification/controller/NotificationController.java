package org.example.autoreview.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.service.NotificationDtoService;
import org.example.autoreview.global.scheduler.NotificationScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "알림 API", description = "알림 API")
@RequestMapping("/v1/api/notification")
@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationScheduler notificationScheduler;
    private final NotificationDtoService notificationDtoService;

    @Operation(summary = "알림 전체 조회", description = "회원 정보는 헤더에서")
    @GetMapping("/list")
    public ResponseEntity<List<NotificationResponseDto>> findAll() {
        return ResponseEntity.ok().body(notificationDtoService.findAll());
    }

    @Operation(summary = "회원 알림 전체 조회", description = "회원 정보는 헤더에서")
    @GetMapping("/own")
    public ResponseEntity<List<NotificationResponseDto>> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(notificationDtoService.findAllByMemberId(userDetails.getUsername()));
    }

    @Operation(summary = "회원 안읽은 알림 전체 조회", description = "회원이 안읽은 알림을 조회한다.")
    @GetMapping("/own/unchecked")
    public ResponseEntity<List<NotificationResponseDto>> findAllNotificationIsNotCheckedByMemberId(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(notificationDtoService.findAllNotificationIsNotCheckedByMemberId(userDetails.getUsername()));
    }

    @Operation(summary = "알림 상태 변경", description = "알림을 읽음 상태로 변경한다.")
    @PutMapping
    public ResponseEntity<String> statUpdate(@RequestParam Long id) {
        notificationDtoService.stateUpdate(id);
        return ResponseEntity.ok().body("update success");
    }

    @Operation(summary = "푸쉬 알림 강제 시작")
    @PutMapping("/push")
    public ResponseEntity<String> push() {
        notificationScheduler.pushNotification();
        return ResponseEntity.ok().body("push notification complete");
    }

    @Operation(summary = "전송 완료된 푸쉬 알림 강제 삭제")
    @DeleteMapping
    public ResponseEntity<String> delete() {
        notificationScheduler.deleteCompleteNotification();
        return ResponseEntity.ok().body("delete notification complete");
    }
}
