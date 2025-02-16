package org.example.autoreview.domain.comment.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c WHERE c.parent is null ORDER BY c.createDate ASC")
    Page<Comment> findByCommentPage(Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.parent.id = :parentId ORDER BY c.createDate ASC")
    Page<Comment> findByReplyPage(@Param("parentId") Long parentId, Pageable pageable);
}
