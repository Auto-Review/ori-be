package org.example.autoreview.domain.comment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.global.common.basetime.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>();

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = false)
    private String nickName;

    // 나중에 길이 제한 설정도 해야 함
    @Column(nullable = false)
    private String body;

    @Builder
    public Comment(Long writerId, String nickName, String body) {
        this.writerId = writerId;
        this.nickName = nickName;
        this.body = body;
    }
}
