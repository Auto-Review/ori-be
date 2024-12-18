package org.example.autoreview.domain.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.dto.request.NotificationRequestDto;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.global.common.basetime.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseEntity {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_post_id")
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

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isChecked;

    @Builder
    public Notification(CodePost codePost, String title, String content, NotificationStatus status, LocalDate executeTime, Member member, boolean isChecked){
        this.codePost = codePost;
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
