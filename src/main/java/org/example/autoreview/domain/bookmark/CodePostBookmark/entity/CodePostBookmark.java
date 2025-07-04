package org.example.autoreview.domain.bookmark.CodePostBookmark.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.global.common.basetime.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "uq_email_codepost",
        columnNames = {"email", "code_post_id"})}
)
@Entity
public class CodePostBookmark extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Long codePostId;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    public CodePostBookmark(Long codePostId, String email, boolean isDeleted) {
        this.codePostId = codePostId;
        this.email = email;
        this.isDeleted = isDeleted;
    }

    public Long update() {
        this.isDeleted = !this.isDeleted;
        return this.id;
    }

}
