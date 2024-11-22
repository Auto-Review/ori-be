package org.example.autoreview.domain.codepost.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.review.entity.Review;
import org.example.autoreview.global.common.basetime.BaseEntity;

@Getter
@NoArgsConstructor
@Entity
public class CodePost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Member member;

    @OneToOne(mappedBy = "codePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Notification notification;

    @OneToMany(mappedBy = "codePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    @Column(length = 100)
    private String title;

    private int level;

    private LocalDate reviewDay;

    @Column(length = 2000)
    private String description;

    @Column(length = 1000)
    private String code;

    @Builder
    public CodePost(String title, int level, LocalDate reviewDay, String description, String code, Member member) {
        this.title = title;
        this.member = member;
        this.level = level;
        this.reviewDay = reviewDay;
        this.description = description;
        this.code = code;
    }

    public void update(CodePostUpdateRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.level = requestDto.getLevel();
        this.reviewDay = requestDto.getReviewDay();
        this.description = requestDto.getDescription();
        this.code = requestDto.getCode();
    }
}
