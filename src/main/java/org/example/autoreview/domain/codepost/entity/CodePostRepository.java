package org.example.autoreview.domain.codepost.entity;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodePostRepository extends JpaRepository<CodePost, Long> {

    @Query("SELECT c FROM CodePost c ORDER BY c.id DESC")
    Page<CodePost> findByPage(Pageable pageable);

    @Query("SELECT c FROM CodePost c WHERE c.title LIKE %:keyword% ORDER BY c.id DESC")
    Page<CodePost> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT c FROM CodePost c WHERE c.member.id =:id AND c.title LIKE %:keyword% ORDER BY c.id DESC")
    Page<CodePost> mySearch(@Param("keyword") String keyword, Pageable pageable, @Param("id") Long id);

    @Query("SELECT c FROM CodePost c WHERE c.member.id =:id ORDER BY c.id DESC")
    Page<CodePost> findByMemberId(@Param("id") Long id, Pageable pageable);

}
