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

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = false)
    private String writerNickName;

    @Column(nullable = true)
    private String targetNickName;

    @Column(nullable = false)
    private String body;

    // 공통 필드 및 메서드
    protected Comment(String targetNickName, String body, Long writerId, String writerNickName) {
        this.targetNickName = targetNickName;
        this.body = body;
        this.writerId = writerId;
        this.writerNickName = writerNickName;
    }

    public void update(CommentUpdateRequestDto requestDto) {
        this.writerNickName = requestDto.writerNickName();
        this.targetNickName = requestDto.targetNickName();
        this.body = requestDto.body();
    }

    public abstract Long getParentId();

}
