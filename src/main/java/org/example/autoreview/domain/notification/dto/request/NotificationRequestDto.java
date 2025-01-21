package org.example.autoreview.domain.notification.dto.request;

import java.time.LocalDate;
import lombok.Getter;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.entity.Notification;
import org.example.autoreview.domain.notification.enums.NotificationStatus;

@Getter
public class NotificationRequestDto {
    private Long id;
    private LocalDate reviewDay;

    public Notification toEntity(Member member, CodePost codePost){
        return Notification.builder()
                .title("ORI 복습 알림")
                .content(codePost.getTitle())
                .status(NotificationStatus.PENDING)
                .executeTime(reviewDay)
                .member(member)
                .codePost(codePost)
                .build();
    }
}
