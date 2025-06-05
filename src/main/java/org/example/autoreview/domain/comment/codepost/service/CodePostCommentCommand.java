package org.example.autoreview.domain.comment.codepost.service;

import org.example.autoreview.domain.comment.base.CommentCommand;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;
import org.example.autoreview.domain.comment.codepost.entity.CodePostCommentRepository;
import org.springframework.stereotype.Component;

@Component
public class CodePostCommentCommand extends CommentCommand<CodePostComment, CodePostCommentRepository> {

    public CodePostCommentCommand(CodePostCommentRepository repository) {
        super(repository);
    }

}
