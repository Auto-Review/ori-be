package org.example.autoreview.domain.comment.til.entity;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.autoreview.domain.comment.base.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface TilPostCommentRepository extends CommentRepository<TilPostComment> {

    @Query("SELECT c FROM TilPostComment c WHERE c.tilPost.id = :postId AND c.parent is null ORDER BY c.createDate ASC")
    Page<TilPostComment> findByCommentPage(@Param("postId") Long postId, Pageable pageable);

    @Query("SELECT c FROM TilPostComment c WHERE c.tilPost.id = :postId AND c.parent.id = :parentId ORDER BY c.createDate ASC")
    Page<TilPostComment> findByReplyPage(@Param("postId") Long postId, @Param("parentId") Long parentId, Pageable pageable);
}
