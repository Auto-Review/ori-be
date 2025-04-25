package org.example.autoreview.domain.codepost.entity;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodePostRepository extends JpaRepository<CodePost, Long> {

    @Query("SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c,m) " +
            "FROM CodePost c INNER JOIN Member m ON c.writerId = m.id ORDER BY c.id DESC")
    Page<CodePostThumbnailResponseDto> findByPage(Pageable pageable);

    @Query(value = "SELECT * FROM code_post c WHERE MATCH(c.title) AGAINST(:keyword IN BOOLEAN MODE)",
            countQuery = "SELECT COUNT(*) FROM code_post c WHERE MATCH(c.title) AGAINST(:keyword IN BOOLEAN MODE)",
            nativeQuery = true)
    Page<CodePost> search(@Param("keyword") String keyword, Pageable pageable);


    @Query("SELECT c FROM CodePost c WHERE c.writerId =:id AND c.title LIKE %:keyword% ORDER BY c.id DESC")
    Page<CodePost> mySearch(@Param("keyword") String keyword, Pageable pageable, @Param("id") Long id);

    @Query("SELECT c FROM CodePost c WHERE c.writerId =:id ORDER BY c.id DESC")
    Page<CodePost> findByMemberId(@Param("id") Long id, Pageable pageable);

}
