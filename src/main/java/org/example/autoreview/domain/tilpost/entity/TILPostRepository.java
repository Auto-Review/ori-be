package org.example.autoreview.domain.tilpost.entity;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TILPostRepository extends JpaRepository<TILPost,Long> {

    @Query("SELECT p FROM TILPost p ORDER BY p.id DESC")
    Page<TILPost> findAll(Pageable pageable);

    Page<TILPost> findByTitleContaining(String keyword, Pageable pageable);
    Page<TILPost> findTILPostsByMemberIdOrderByIdDesc(Long Id, Pageable pageable);
    Page<TILPost> findTILPostsByMemberIdAndTitleContainingOrderByIdDesc(Long Id, String title, Pageable pageable);
    List<TILPost> findAllByOrderByIdDesc(Pageable pageable);
    List<TILPost> findByIdLessThanOrderByIdDesc(@Param("id") long id, Pageable pageable);

    Page<TILPost> findByIdInOrderByIdDesc(Collection<Long> ids, Pageable pageable);
}
