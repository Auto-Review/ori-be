package org.example.autoreview.domain.bookmark.TILBookmark.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TILBookmarkRepository extends JpaRepository<TILBookmark, TILBookmarkId> {

    Page<TILBookmark> findTILBookmarksByEmailAndIsBookmarked(String email, Boolean flag, Pageable pageable);

    List<TILBookmark> findTILBookmarksByIsBookmarked(Boolean flag);
}
