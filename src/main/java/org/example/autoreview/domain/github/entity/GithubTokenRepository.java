package org.example.autoreview.domain.github.entity;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubTokenRepository extends JpaRepository<GithubToken,Long> {

    Optional<GithubToken> findByEmail(@Param("email") String email);

    boolean existsByEmail(@Param("email") String email);
}
