package org.example.autoreview.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.domain.NotificationRepository;
import org.example.autoreview.domain.notification.dto.request.NotificationSaveRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private Long save(NotificationSaveRequestDto requestDto){
        Notification notification = requestDto.toEntity();
        return notification.getId();
    }

    private void sendNotification(Notification notification) {
        // 여기에서 알림을 전송하는 로직을 구현합니다.
        System.out.println("알림 발송: " + notification.getContent() + " 시간: " + LocalDateTime.now());
    }
}
