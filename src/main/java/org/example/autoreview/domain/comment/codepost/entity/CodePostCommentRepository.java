package org.example.autoreview.domain.comment.codepost.entity;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.autoreview.domain.comment.base.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface CodePostCommentRepository extends CommentRepository<CodePostComment> {

    @Query("SELECT c FROM CodePostComment c WHERE c.codePost.id = :postId AND c.parent is null ORDER BY c.createDate ASC")
    Page<CodePostComment> findByCommentPage(@Param("postId") Long postId, Pageable pageable);

    @Query("SELECT c FROM CodePostComment c WHERE c.codePost.id = :postId AND c.parent.id = :parentId ORDER BY c.createDate ASC")
    Page<CodePostComment> findByReplyPage(@Param("postId") Long postId, @Param("parentId") Long parentId, Pageable pageable);
}
