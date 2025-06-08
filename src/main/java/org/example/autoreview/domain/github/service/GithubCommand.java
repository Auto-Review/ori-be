package org.example.autoreview.domain.github.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.github.entity.GithubToken;
import org.example.autoreview.domain.github.entity.GithubTokenRepository;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class GithubCommand {

    private final GithubTokenRepository githubTokenRepository;

    @Transactional
    public GithubToken save(GithubToken githubToken) {
        return githubTokenRepository.save(githubToken);
    }

    @Transactional
    public GithubToken update(Long githubTokenId, String newToken) {
        GithubToken githubToken = githubTokenRepository.findById(githubTokenId).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_GITHUB_TOKEN)
        );
        return githubToken.update(newToken);
    }

    @Transactional(readOnly = true)
    public GithubToken findByEmail(String email) {
        return githubTokenRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_GITHUB_TOKEN)
        );
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return githubTokenRepository.existsByEmail(email);
    }

}
