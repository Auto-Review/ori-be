package org.example.autoreview.domain.codepost.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;
import org.example.autoreview.domain.review.entity.Review;
import org.example.autoreview.global.common.basetime.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class CodePost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "codePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "codePost", cascade = CascadeType.ALL)
    private List<CodePostComment> codePostCommentList = new ArrayList<>();

    @Column(nullable = false)
    private Long writerId;

    @Column(length = 100)
    private String title;

    private int level;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean isPublic;

    private LocalDate reviewDay;

    @Column(length = 4000)
    private String description;

    @Column(length = 4000, columnDefinition = "LONGTEXT")
    private String code;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Builder
    public CodePost(String title, int level, LocalDate reviewDay, String description,
                    String code, Language language, Long writerId, boolean isPublic) {
        this.writerId = writerId;
        this.title = title;
        this.level = level;
        this.reviewDay = reviewDay;
        this.description = description;
        this.code = code;
        this.language = language;
        this.isPublic = isPublic;
    }

    public void update(CodePostUpdateRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.level = requestDto.getLevel();
        this.isPublic = requestDto.isPublic();
        this.reviewDay = requestDto.getReviewDay();
        this.description = requestDto.getDescription();
        this.language = Language.of(requestDto.getLanguage());
        this.code = requestDto.getCode();
    }
}
