package org.example.autoreview.domain.notification.dto.request;

import lombok.Getter;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.enums.NotificationStatus;

import java.time.LocalDate;

@Getter
public class NotificationSaveRequestDto {

    private String content;
    private LocalDate reviewDay;

    public Notification toEntity(Member member, CodePost codePost){
        return Notification.builder()
                .title("ORI 복습 알림")
                .content(content)
                .status(NotificationStatus.PENDING)
                .executeTime(reviewDay)
                .member(member)
                .codePost(codePost)
                .build();
    }
}
