package org.example.autoreview.domain.bookmark.TILBookmark.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TILBookmarkId implements Serializable {
    private String email;
    private Long postId;
}
