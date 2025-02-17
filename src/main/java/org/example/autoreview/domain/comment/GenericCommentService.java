package org.example.autoreview.domain.comment;

import org.example.autoreview.domain.comment.codepost.dto.request.CommentDeleteRequestDto;
import org.example.autoreview.domain.comment.codepost.dto.request.CommentUpdateRequestDto;
import org.example.autoreview.domain.comment.codepost.dto.response.CommentListResponseDto;
import org.springframework.data.domain.Pageable;

public interface GenericCommentService<T, P> {
    Long save(T requestDto, String email);
    CommentListResponseDto findByCommentPage(Long postId, Pageable pageable);
    CommentListResponseDto findByReplyPage(Long postId, Long parentId, Pageable pageable);
    Long update(CommentUpdateRequestDto requestDto, String email);
    Long delete(CommentDeleteRequestDto requestDto, String email);
}

