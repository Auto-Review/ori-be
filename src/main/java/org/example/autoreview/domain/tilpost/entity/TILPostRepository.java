package org.example.autoreview.domain.tilpost.entity;

import java.util.Collection;
import java.util.List;

import org.example.autoreview.domain.tilpost.dto.response.TILPostThumbnailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TILPostRepository extends JpaRepository<TILPost,Long> {

    @Query("SELECT new org.example.autoreview.domain.tilpost.dto.response.TILPostThumbnailResponseDto(p,m) " +
            "FROM TILPost p INNER JOIN Member m on p.writerId = m.id ORDER BY p.id DESC")
    Page<TILPostThumbnailResponseDto> findDtoAll(Pageable pageable);

    @Query("SELECT new org.example.autoreview.domain.tilpost.dto.response.TILPostThumbnailResponseDto(p,m)" +
            "FROM TILPost p INNER JOIN Member m ON p.writerId = m.id WHERE p.title LIKE %:keyword%")
    Page<TILPostThumbnailResponseDto> findByTitleContaining(String keyword, Pageable pageable);

    Page<TILPost> findTILPostsByWriterIdOrderByIdDesc(Long Id, Pageable pageable);
    Page<TILPost> findTILPostsByWriterIdAndTitleContainingOrderByIdDesc(Long Id, String title, Pageable pageable);
    List<TILPost> findAllByOrderByIdDesc(Pageable pageable);
    List<TILPost> findByIdLessThanOrderByIdDesc(@Param("id") long id, Pageable pageable);

    @Query("SELECT new org.example.autoreview.domain.tilpost.dto.response.TILPostThumbnailResponseDto(p,m)" +
            "FROM TILPost p INNER JOIN Member m ON p.writerId = m.id WHERE p.id IN :ids ORDER BY p.id DESC")
    Page<TILPostThumbnailResponseDto> findByIdInOrderByIdDesc(Collection<Long> ids, Pageable pageable);
}
