package org.example.autoreview.domain.bookmark.CodePostBookmark.entity;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CodePostBookmarkRepository extends JpaRepository<CodePostBookmark,Long> {

    @Modifying
    @Query(
        value = " INSERT INTO code_post_bookmark (member_id, code_post_id, is_deleted, create_date, update_date) " +
                " VALUES (:memberId, :codePostId, false, NOW(), NOW()) " +
                " ON DUPLICATE KEY UPDATE is_deleted = NOT is_deleted, update_date = NOW() ",
        nativeQuery = true
    )
    void upsert(@Param("memberId") Long memberId, @Param("codePostId") Long codePostId);


    @Query("SELECT b FROM CodePostBookmark b WHERE b.member.id = :memberId AND b.codePostId = :codePostId")
    Optional<CodePostBookmark> findById(@Param("memberId") Long memberId, @Param("codePostId") Long codePostId);

    @Query("SELECT new org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkResponseDto(b,c,m) from CodePostBookmark b "
            + "JOIN CodePost c ON b.codePostId = c.id JOIN Member m ON m.id = c.writerId WHERE b.isDeleted = FALSE AND b.member.id = :memberId")
    Page<CodePostBookmarkResponseDto> findAllByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM CodePostBookmark b WHERE b.isDeleted = TRUE")
    void deleteExpiredSoftDeletedBookmarks();
}
