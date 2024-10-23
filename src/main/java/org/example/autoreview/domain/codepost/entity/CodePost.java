package org.example.autoreview.domain.codepost.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.common.basetime.BaseEntity;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.member.entity.Member;

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
        member.getCodePosts().add(this);
    }

    public void update(CodePostUpdateRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.level = requestDto.getLevel();
        this.reviewTime = requestDto.getReviewTime();
        this.description = requestDto.getDescription();
        this.code = requestDto.getCode();
    }
}
