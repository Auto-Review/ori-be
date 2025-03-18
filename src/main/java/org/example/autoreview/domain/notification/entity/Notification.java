package org.example.autoreview.domain.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.global.common.basetime.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long codePostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content; // post_title

    @Column(nullable = false)
    private LocalDate executeTime; // post_review_day

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isChecked;

    @Builder
    public Notification(Long codePostId, String title, String content, NotificationStatus status, LocalDate executeTime, Member member, boolean isChecked){
        this.codePostId = codePostId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.executeTime = executeTime;
        this.isChecked = isChecked;
        this.member = member;
    }

    public void updateStatusToComplete() {
        this.status = NotificationStatus.COMPLETE;
    }

    public void readNotification() { this.isChecked = true; }

    public void update(CodePost codePost, NotificationStatus status) {
        this.status = status;
        this.content = codePost.getTitle();
        this.executeTime = codePost.getReviewDay();
    }
}
