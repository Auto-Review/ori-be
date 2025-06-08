package org.example.autoreview.domain.comment.base;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.comment.base.dto.request.CommentUpdateRequestDto;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class CommentCommand<C extends Comment, R extends CommentRepository<C>> {

    protected final R repository;

    @Transactional
    public C save(C comment) {
        return repository.save(comment);
    }

    @Transactional(readOnly = true)
    public C findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_COMMENT)
        );
    }

    @Transactional(readOnly = true)
    public Page<C> findByCommentPage(Long id, Pageable pageable) {
        return repository.findByCommentPage(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<C> findByReplyPage(Long id, Long parentId, Pageable pageable) {
        return repository.findByReplyPage(id, parentId, pageable);
    }

    @Transactional
    public void update(Long commentId, CommentUpdateRequestDto requestDto) {
        C comment = repository.findById(commentId).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_COMMENT)
        );
        comment.update(requestDto);
    }

    @Transactional
    public void delete(C comment) {
        repository.delete(comment);
    }


}
