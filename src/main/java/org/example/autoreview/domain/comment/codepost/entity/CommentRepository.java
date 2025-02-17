package org.example.autoreview.domain.comment.codepost.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<CodePostComment,Long> {

    // code post를 먼저 찾고 진행할지 comment에서 바로 찾을지 고민
    // @Query("SELECT c FROM CodePost cp JOIN cp.commentList c
    //         WHERE cp.id = :postId AND c.parent IS NULL ORDER BY c.createDate ASC")
    @Query("SELECT c FROM CodePostComment c WHERE c.codePost.id = :postId AND c.parent is null ORDER BY c.createDate ASC")
    Page<CodePostComment> findByCommentPage(@Param("postId") Long postId, Pageable pageable);

    @Query("SELECT c FROM CodePostComment c WHERE c.codePost.id = :postId AND c.parent.id = :parentId ORDER BY c.createDate ASC")
    Page<CodePostComment> findByReplyPage(@Param("postId") Long postId, @Param("parentId") Long parentId, Pageable pageable);
}
