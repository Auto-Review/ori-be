package org.example.autoreview.domain.codepost.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.review.entity.Review;
import org.example.autoreview.global.common.basetime.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class CodePost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Member member;

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
