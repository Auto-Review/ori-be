package org.example.autoreview.domain.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.scheduler.domain.Notification;
import org.example.autoreview.domain.scheduler.domain.NotificationRepository;
import org.example.autoreview.domain.scheduler.enums.NotificationStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final TaskScheduler taskScheduler;

    private final NotificationRepository scheduledMessageRepository;

    public void scheduleNotification(String notification, LocalDateTime dateTime) {
        Notification scheduledNotification = Notification.builder()
                .content(notification)
                .executeTime(dateTime)
                .status(NotificationStatus.PENDING)
                .build();

        scheduledMessageRepository.save(scheduledNotification);

        long delay = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();

        taskScheduler.schedule(() -> {
            sendNotification(scheduledNotification);
            scheduledNotification.notificationStatusUpdate();
            scheduledMessageRepository.save(scheduledNotification);
        }, Instant.ofEpochSecond(delay));
    }

    private void sendNotification(Notification notification) {
        // 여기에서 알림을 전송하는 로직을 구현합니다.
        System.out.println("알림 발송: " + notification.getContent() + " 시간: " + LocalDateTime.now());
    }
}
