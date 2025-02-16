package org.example.autoreview.domain.comment.entity;

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
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.comment.dto.request.CommentUpdateRequestDto;
import org.example.autoreview.global.common.basetime.BaseEntity;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_post_id")
    private CodePost codePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>();

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = false)
    private String writerNickName;

    @Column(nullable = true)
    private String targetNickName;

    // 나중에 길이 제한 설정도 해야 함
    @Column(nullable = false)
    private String body;

    @Builder
    public Comment(CodePost codePost, Comment parent, String targetNickName, String body, Long writerId, String writerNickName) {
        this.codePost = codePost;
        this.parent = parent;
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
}
