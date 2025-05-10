package org.example.autoreview.domain.bookmark.CodePostBookmark.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.global.common.basetime.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Entity
public class CodePostBookmark extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Column(nullable = false)
    private Long codePostId;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    public CodePostBookmark(Long codePostId, Member member, boolean isDeleted) {
        this.codePostId = codePostId;
        this.member = member;
        this.isDeleted = isDeleted;
    }

    public Long update() {
        this.isDeleted = !this.isDeleted;
        return this.id;
    }

}
