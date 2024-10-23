package org.example.autoreview.domain.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.notification.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationScheduler {

    private final NotificationService notificationService;

    // 매일 오전 8시에 호출
    @Scheduled(cron = "0 0 8 * * ?")
    public void pushNotification(){
        log.info("start push notification");
        notificationService.sendNotification();
        log.info("finish push notification");

    }

    // 매일 오전 0시에 호출
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteCompleteNotification(){
        // 알림 서비스에서 push notification 메서드 호출
        log.info("delete push notification");
    }
}
