package org.example.autoreview.domain.notification.dto.response;

import java.time.LocalDate;
import lombok.Getter;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.enums.NotificationStatus;

@Getter
public class NotificationResponseDto {

    private final Long id;
    private final String content;
    private final LocalDate executeTime;
    private final NotificationStatus status;

    public NotificationResponseDto(Notification entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.executeTime = entity.getExecuteTime();
        this.status = entity.getStatus();
    }
}
