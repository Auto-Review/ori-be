package org.example.autoreview.domain.notification.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.dto.request.NotificationUpdateRequestDto;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.global.common.basetime.BaseEntity;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseEntity {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private CodePost codePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate executeTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Builder
    public  Notification(CodePost codePost, String title, String content, NotificationStatus status, LocalDate executeTime, Member member){
        this.codePost = codePost;
        this.title = title;
        this.content = content;
        this.status = status;
        this.executeTime = executeTime;
        this.member = member;
    }

    public void notificationStatusUpdate() {
        this.status = NotificationStatus.COMPLETE;
    }

    public void update(NotificationUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.executeTime = requestDto.getReviewDay();
    }
}
