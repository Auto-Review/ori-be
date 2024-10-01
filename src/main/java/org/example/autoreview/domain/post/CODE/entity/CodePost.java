package org.example.autoreview.domain.post.CODE.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.common.basetime.BaseEntity;
import org.example.autoreview.domain.post.CODE.dto.request.CodePostUpdateRequestDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class CodePost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_post_id")
    private Long id;

    @Column(length = 100)
    private String title;

    private int level;

    private LocalDateTime reviewTime;

    @Column(length = 2000)
    private String content;

    @Column(length = 1000)
    private String code;

    @Builder
    public CodePost(String title, int level, LocalDateTime reviewTime, String content, String code) {
        this.title = title;
        this.level = level;
        this.reviewTime = reviewTime;
        this.content = content;
        this.code = code;
    }

    public void update(CodePostUpdateRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.level = requestDto.getLevel();
        this.reviewTime = requestDto.getReviewTime();
        this.content = requestDto.getContent();
        this.code = requestDto.getCode();
    }
}
