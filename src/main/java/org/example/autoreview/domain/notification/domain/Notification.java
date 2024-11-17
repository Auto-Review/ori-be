package org.example.autoreview.domain.notification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.global.common.basetime.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private LocalDate executeTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Builder
    public Notification(String title, String content, NotificationStatus status, LocalDate executeTime, Member member){
        this.title = title;
        this.content = content;
        this.status = status;
        this.executeTime = executeTime;
        this.member = member;
    }

    public void notificationStatusUpdate() {
        this.status = NotificationStatus.COMPLETE;
    }
}
