package org.example.autoreview.domain.comment.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface CommentRepository<C extends Comment> extends JpaRepository<C, Long> {

    Page<C> findByCommentPage(@Param("postId") Long postId, Pageable pageable);
    Page<C> findByReplyPage(@Param("postId") Long postId, @Param("parentId") Long parentId, Pageable pageable);
}
