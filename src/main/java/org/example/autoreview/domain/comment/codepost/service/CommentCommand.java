package org.example.autoreview.domain.comment.codepost.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;
import org.example.autoreview.domain.comment.codepost.entity.CommentRepository;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentCommand {

    private final CommentRepository commentRepository;

    public CodePostComment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_COMMENT)
        );
    }
}
