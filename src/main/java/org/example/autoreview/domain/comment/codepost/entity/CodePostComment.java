package org.example.autoreview.domain.comment.codepost.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.comment.base.Comment;

@Getter
@NoArgsConstructor
@Entity
public class CodePostComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_post_id")
    private CodePost codePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private CodePostComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<CodePostComment> children = new ArrayList<>();

    @Builder
    public CodePostComment(CodePost codePost, CodePostComment parent, String mentionNickName, String mentionEmail,
                           String body, boolean isPublic, Long writerId) {
        super(mentionNickName, mentionEmail, body, isPublic, writerId);
        this.codePost = codePost;
        this.parent = parent;
    }

    @Override
    public Long getParentId() {
        return this.parent != null ? this.parent.getId() : null;
    }
}
