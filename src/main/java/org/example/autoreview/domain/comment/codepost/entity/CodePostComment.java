package org.example.autoreview.domain.comment.codepost.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.comment.BaseComment;

@Getter
@NoArgsConstructor
@Entity
public class CodePostComment extends BaseComment {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_post_id")
    private CodePost codePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private CodePostComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<CodePostComment> children = new ArrayList<>();

    @Builder
    public CodePostComment(CodePost codePost, CodePostComment parent, String targetNickName, String body, Long writerId, String writerNickName) {
        super(targetNickName, body, writerId, writerNickName);
        this.codePost = codePost;
        this.parent = parent;

    }
}
