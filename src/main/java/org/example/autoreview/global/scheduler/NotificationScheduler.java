package org.example.autoreview.global.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.notification.service.NotificationDtoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationScheduler {

    private final NotificationDtoService notificationMemberService;

    // 매일 오전 8시에 호출
    @Scheduled(cron = "0 0 8 * * ?")
    public void pushNotification(){
        log.info("start push notification");
        notificationMemberService.sendNotification();
        log.info("finish push notification");

    }

    // 매달 10일 오전 7시에 호출
    @Scheduled(cron = "0 10 7 * * ?")
    public void deleteCompleteNotification(){
        log.info("start delete notification");
        notificationMemberService.deleteCompleteNotification();
        log.info("end delete notification");
    }
}
