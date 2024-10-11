package org.example.autoreview.domain.scheduler.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.common.basetime.BaseEntity;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.scheduler.enums.NotificationStatus;

import java.time.LocalDateTime;

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
    private String content;

    @NotNull
    private LocalDateTime executeTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Builder
    public Notification(String content, NotificationStatus status, LocalDateTime executeTime){
        this.content = content;
        this.status = status;
        this.executeTime = executeTime;
    }

    public void setMember(Member member){
        this.member = member;
        member.addNotification(this);
    }

    public void notificationStatusUpdate() {
        this.status = NotificationStatus.COMPLETE;
    }
}
