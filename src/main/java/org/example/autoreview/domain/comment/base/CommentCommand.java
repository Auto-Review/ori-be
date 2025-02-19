package org.example.autoreview.domain.comment.base;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;

@RequiredArgsConstructor
public abstract class CommentCommand<C extends Comment, R extends CommentRepository<C>> {

    protected final R repository;

    public C findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_COMMENT)
        );
    }
}
