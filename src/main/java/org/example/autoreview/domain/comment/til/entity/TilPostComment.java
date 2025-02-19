package org.example.autoreview.domain.comment.til.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.comment.base.Comment;
import org.example.autoreview.domain.tilpost.entity.TILPost;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class TilPostComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "til_post_id")
    private TILPost tilPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private TilPostComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<TilPostComment> children = new ArrayList<>();

    @Builder
    public TilPostComment(TILPost tilPost, TilPostComment parent, String targetNickName, String body, Long writerId, String writerNickName) {
        super(targetNickName, body, writerId, writerNickName);
        this.tilPost = tilPost;
        this.parent = parent;

    }

    @Override
    public Long getParentId() {
        return this.parent != null ? this.parent.getId() : null;
    }
}
