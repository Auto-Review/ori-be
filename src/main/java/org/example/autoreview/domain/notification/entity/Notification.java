package org.example.autoreview.domain.notification.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.dto.request.NotificationRequestDto;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.global.common.basetime.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

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
    private String content;

    @Column(nullable = false)
    private LocalDate executeTime;

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

    public void statusUpdateToComplete() {
        this.status = NotificationStatus.COMPLETE;
    }

    public void checkUpdateToTrue() { this.isChecked = true; }

    public void contentUpdateByCodePostTitle(String content) {
        this.content = content;
    }

    public void update(NotificationRequestDto requestDto) {
        this.status = NotificationStatus.PENDING;
        this.executeTime = requestDto.getReviewDay();
    }
}
