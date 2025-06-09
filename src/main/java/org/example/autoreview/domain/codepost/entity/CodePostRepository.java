package org.example.autoreview.domain.codepost.entity;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodePostRepository extends JpaRepository<CodePost, Long> {

    @Query("SELECT c FROM CodePost c WHERE c.id = :id AND (c.writerId = :memberId OR c.isPublic = TRUE)")
    Optional<CodePost> findByIdIsPublic(@Param("id") Long id, @Param("memberId") Long memberId);

    @Query("""
        SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c,m)
        FROM CodePost c
        INNER JOIN Member m ON c.writerId = m.id
        AND c.isPublic = TRUE
        AND (:language = 'all' OR c.language = :language)
    """)
    Page<CodePostThumbnailResponseDto> findByPage(Pageable pageable, @Param("language") Language language);

    @Query(value = "SELECT * FROM code_post c WHERE c.is_public = TRUE AND MATCH(c.title) AGAINST(:keyword IN BOOLEAN MODE)",
            countQuery = "SELECT COUNT(*) FROM code_post c WHERE MATCH(c.title) AGAINST(:keyword IN BOOLEAN MODE)",
            nativeQuery = true)
    Page<CodePost> search(@Param("keyword") String keyword, Pageable pageable);


    @Query("SELECT c FROM CodePost c WHERE c.writerId =:id AND c.title LIKE %:keyword% ORDER BY c.id DESC")
    Page<CodePost> mySearch(@Param("keyword") String keyword, Pageable pageable, @Param("id") Long id);

    @Query("""
        SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c, m)
        FROM CodePost c
        LEFT JOIN c.codePostCommentList cm
        INNER JOIN Member m ON c.writerId = m.id
        WHERE c.writerId = :writerId
        AND LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        AND (:language = 'all' OR c.language = :language)
        GROUP BY c, m
        ORDER BY COUNT(cm) DESC
    """)
    Page<CodePostThumbnailResponseDto> mySearchSortByCommentCountDesc(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("writerId") Long writerId,
            @Param("language") Language language
    );

    @Query("""
        SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c, m)
        FROM CodePost c
        LEFT JOIN c.codePostCommentList cm
        INNER JOIN Member m ON c.writerId = m.id
        WHERE c.writerId = :writerId
        AND LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        AND (:language = 'all' OR c.language = :language)
        GROUP BY c, m
        ORDER BY COUNT(cm) ASC
    """)
    Page<CodePostThumbnailResponseDto> mySearchSortByCommentCountAsc(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("writerId") Long writerId,
            @Param("language") Language language
    );


    @Query("SELECT c FROM CodePost c WHERE c.writerId =:id")
    Page<CodePost> findByMemberId(@Param("id") Long id, Pageable pageable);

    @Query("""
        SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c, m)
        FROM CodePost c
        LEFT JOIN c.codePostCommentList cm
        INNER JOIN Member m ON c.writerId = m.id
        WHERE c.writerId = :memberId
          AND c.isPublic = TRUE
          AND (:language = 'all' OR c.language = :language)
        GROUP BY c, m
        ORDER BY COUNT(cm) DESC
    """)
    Page<CodePostThumbnailResponseDto> findByMemberIdSortByCommentCountDesc(Pageable pageable, @Param("memberId") Long memberId, @Param("language") Language language);

    @Query("""
        SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c, m)
        FROM CodePost c
        LEFT JOIN c.codePostCommentList cm
        INNER JOIN Member m ON c.writerId = m.id
        WHERE c.writerId = :memberId
          AND c.isPublic = TRUE
          AND (:language = 'all' OR c.language = :language)
        GROUP BY c, m
        ORDER BY COUNT(cm) ASC
    """)
    Page<CodePostThumbnailResponseDto> findByMemberIdSortByCommentCountAsc(Pageable pageable, @Param("memberId") Long memberId, @Param("language") Language language);

    @Query("""
        SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c, m)
        FROM CodePost c
        LEFT JOIN c.codePostCommentList cm
        INNER JOIN Member m ON c.writerId = m.id
        AND c.isPublic = TRUE
        AND LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        AND (:language = 'all' OR c.language = :language)
        GROUP BY c, m
        ORDER BY COUNT(cm) DESC
    """)
    Page<CodePostThumbnailResponseDto> findByPageSortByCommentCountDesc(Pageable pageable, @Param("language") Language language);

    @Query("""
        SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c, m)
        FROM CodePost c
        LEFT JOIN c.codePostCommentList cm
        INNER JOIN Member m ON c.writerId = m.id
        AND c.isPublic = TRUE
        AND (:language = 'all' OR c.language = :language)
        GROUP BY c, m
        ORDER BY COUNT(cm) ASC
    """)
    Page<CodePostThumbnailResponseDto> findByPageSortByCommentCountAsc(Pageable pageable, @Param("language") Language language);

    @Query("""
        SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c, m)
        FROM CodePost c
        LEFT JOIN c.codePostCommentList cm
        INNER JOIN Member m ON c.writerId = m.id
        AND c.isPublic = TRUE
        AND LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        AND (:language = 'all' OR c.language = :language)
        GROUP BY c, m
        ORDER BY COUNT(cm) DESC
    """)
    Page<CodePostThumbnailResponseDto> searchSortByCommentCountDesc(@Param("keyword") String keyword, Pageable pageable, @Param("language") Language language);

    @Query("""
        SELECT new org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto(c, m)
        FROM CodePost c
        LEFT JOIN c.codePostCommentList cm
        INNER JOIN Member m ON c.writerId = m.id
        AND c.isPublic = TRUE
        AND LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        AND (:language = 'all' OR c.language = :language)
        GROUP BY c, m
        ORDER BY COUNT(cm) ASC
    """)
    Page<CodePostThumbnailResponseDto> searchSortByCommentCountAsc(@Param("keyword") String keyword, Pageable pageable, @Param("language") Language language);


}
