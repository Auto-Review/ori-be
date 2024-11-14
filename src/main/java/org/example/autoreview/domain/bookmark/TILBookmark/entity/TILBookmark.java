package org.example.autoreview.domain.bookmark.TILBookmark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.tilpost.entity.TILPost;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TILBookmarkId.class)
@Entity
public class TILBookmark {

    @Id
    @Column(name = "MEMBER_email")
    private String email;

    @Id
    @Column(name = "TILPOST_id")
    private Long postId;

    @Column(nullable = true)
    private Boolean isBookmarked;

    public TILBookmark update(Boolean isBookmarked){
        this.isBookmarked = !isBookmarked;
        return this;
    }
}
