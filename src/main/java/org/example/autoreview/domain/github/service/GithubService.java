package org.example.autoreview.domain.github.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.github.dto.request.GithubCodeRequestDto;
import org.example.autoreview.domain.github.dto.request.GithubTokenRequestDto;
import org.example.autoreview.domain.github.entity.GithubToken;
import org.example.autoreview.domain.github.entity.GithubTokenRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GithubService {

    @Value("${github.client-id}")
    private String clientId;

    @Value("${github.client-secret}")
    private String clientSecret;

    private final GithubTokenRepository githubTokenRepository;
    private final WebClient webClient;

    @Transactional
    public Long save(String accessToken, String email) {
        GithubToken githubToken = GithubToken.builder()
                .githubToken(accessToken)
                .email(email)
                .build();

        return githubTokenRepository.save(githubToken).getId();
    }

    @Transactional
    public Long update(GithubTokenRequestDto requestDto) {
        GithubToken githubToken = githubTokenRepository.findByEmail(requestDto.email()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_GITHUB_TOKEN)
        );
        return githubToken.update(requestDto.githubToken());
    }

    public boolean tokenCheck(String email) {
        return githubTokenRepository.existsByEmail(email);
    }

    public String getAccessToken(GithubCodeRequestDto requestDto) {
        Map<String, String> body = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", requestDto.code()
        );

        Map response = webClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .header(HttpHeaders.ACCEPT, "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return (String) response.get("access_token");

    }
}
