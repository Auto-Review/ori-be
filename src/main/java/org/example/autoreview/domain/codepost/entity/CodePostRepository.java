package org.example.autoreview.domain.codepost.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CodePostRepository extends JpaRepository<CodePost,Long> {

    @Query("SELECT c FROM CodePost c ORDER BY c.id DESC")
    Page<CodePost> findByPage(Pageable pageable);
}
