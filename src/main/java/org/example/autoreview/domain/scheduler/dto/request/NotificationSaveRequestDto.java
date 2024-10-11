package org.example.autoreview.domain.scheduler.dto.request;

import org.example.autoreview.domain.scheduler.domain.Notification;
import org.example.autoreview.domain.scheduler.enums.NotificationStatus;

public class NotificationSaveRequestDto {

    private String content;

    private NotificationStatus status;

    public Notification toEntity(){
        return Notification.builder()
                .content(content)
                .status(status)
                .build();
    }
}
