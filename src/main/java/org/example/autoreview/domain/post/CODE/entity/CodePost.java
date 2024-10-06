package org.example.autoreview.domain.post.CODE.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.common.basetime.BaseEntity;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.post.CODE.dto.request.CodePostUpdateRequestDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class CodePost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Member member;

    @Column(length = 100)
    private String title;

    private int level;

    private LocalDateTime reviewTime;

    @Column(length = 2000)
    private String description;

    @Column(length = 1000)
    private String code;

    @Builder
    public CodePost(String title, int level, LocalDateTime reviewTime, String description, String code) {
        this.title = title;
        this.level = level;
        this.reviewTime = reviewTime;
        this.description = description;
        this.code = code;
    }

    //=연관 관계 편의 메서드=//
    public void setMember(Member member){
        this.member = member;
        member.addCodePost(this);
    }

    public void update(CodePostUpdateRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.level = requestDto.getLevel();
        this.reviewTime = requestDto.getReviewTime();
        this.description = requestDto.getDescription();
        this.code = requestDto.getCode();
    }
}
