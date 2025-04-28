package org.example.autoreview.domain.comment.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.comment.base.dto.request.CommentUpdateRequestDto;
import org.example.autoreview.global.common.basetime.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = true)
    private String mentionNickName;

    @Column(nullable = true)
    private String mentionEmail;

    @Column(nullable = false)
    private String body;

    // 댓글 공개 여부 컬럼 추가
    @ColumnDefault("true")
    @Column(nullable = false)
    private boolean isPublic;

    // 공통 필드 및 메서드
    protected Comment(String mentionNickName, String mentionEmail, String body, boolean isPublic, Long writerId) {
        this.mentionNickName = mentionNickName;
        this.mentionEmail = mentionEmail;
        this.body = body;
        this.isPublic = isPublic;
        this.writerId = writerId;
    }

    public void update(CommentUpdateRequestDto requestDto) {
        this.mentionNickName = requestDto.mentionNickName();
        this.mentionEmail = requestDto.mentionEmail();
        this.body = requestDto.body();
        this.isPublic = requestDto.isPublic();
    }

    public abstract Long getParentId();

}
