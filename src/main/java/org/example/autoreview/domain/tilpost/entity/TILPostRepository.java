package org.example.autoreview.domain.tilpost.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TILPostRepository extends JpaRepository<TILPost,Long> {
    Page<TILPost> findAll(Pageable pageable);
}
