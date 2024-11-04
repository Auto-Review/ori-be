package org.example.autoreview.domain.tilpost.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TILPostRepository extends JpaRepository<TILPost,Long> {

    @Query("SELECT p FROM TILPost p ORDER BY p.id DESC")
    Page<TILPost> findAll(Pageable pageable);

    Page<TILPost> findByTitleContaining(String keyword, Pageable pageable);
    List<TILPost> findAllByOrderByIdDesc(Pageable pageable);
    List<TILPost> findByIdLessThanOrderByIdDesc(@Param("id") long id, Pageable pageable);

}
