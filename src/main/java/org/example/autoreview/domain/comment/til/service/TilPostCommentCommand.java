package org.example.autoreview.domain.comment.til.service;

import org.example.autoreview.domain.comment.base.CommentCommand;
import org.example.autoreview.domain.comment.til.entity.TilPostComment;
import org.example.autoreview.domain.comment.til.entity.TilPostCommentRepository;
import org.springframework.stereotype.Component;

@Component
public class TilPostCommentCommand extends CommentCommand<TilPostComment, TilPostCommentRepository> {

    public TilPostCommentCommand(TilPostCommentRepository repository) { super(repository); }
}
