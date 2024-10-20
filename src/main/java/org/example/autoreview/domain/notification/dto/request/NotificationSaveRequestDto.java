package org.example.autoreview.domain.notification.dto.request;

import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.enums.NotificationStatus;

import java.time.LocalDateTime;

public class NotificationSaveRequestDto {

    private String content;

    private NotificationStatus status;

    private LocalDateTime executeTime;

    public Notification toEntity(){
        return Notification.builder()
                .content(content)
                .status(status)
                .executeTime(executeTime)
                .build();
    }
}
