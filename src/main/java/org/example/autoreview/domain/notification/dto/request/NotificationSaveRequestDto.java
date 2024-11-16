package org.example.autoreview.domain.notification.dto.request;

import java.time.LocalDate;
import lombok.Getter;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.enums.NotificationStatus;

@Getter
public class NotificationSaveRequestDto {

    private String content;
    private NotificationStatus status;
    private LocalDate executeTime;

    public Notification toEntity(Member member){
        return Notification.builder()
                .content(content)
                .status(status)
                .executeTime(executeTime)
                .member(member)
                .build();
    }
}
