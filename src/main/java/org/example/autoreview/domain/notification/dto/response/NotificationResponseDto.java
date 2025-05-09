package org.example.autoreview.domain.notification.dto.response;

import java.time.LocalDate;
import lombok.Getter;
import org.example.autoreview.domain.notification.entity.Notification;
import org.example.autoreview.domain.notification.enums.NotificationStatus;

@Getter
public class NotificationResponseDto {

    private final Long id;
    private final Long codePostId;
    private final String content;
    private final LocalDate executeTime;
    private final NotificationStatus status;
    private final boolean isChecked;

    public NotificationResponseDto(Notification entity) {
        this.id = entity.getId();
        this.codePostId = entity.getCodePostId();
        this.content = entity.getContent();
        this.executeTime = entity.getExecuteTime();
        this.status = entity.getStatus();
        this.isChecked = entity.isChecked();
    }
}
