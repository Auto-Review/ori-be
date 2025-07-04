package org.example.autoreview.domain.github.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.global.common.basetime.BaseEntity;

@Getter
@NoArgsConstructor
@Entity
public class GithubToken extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String githubToken;

    @Builder
    public GithubToken(String email, String githubToken) {
        this.email = email;
        this.githubToken = githubToken;
    }

    public GithubToken update(String githubToken) {
        this.githubToken = githubToken;
        return this;
    }

}
