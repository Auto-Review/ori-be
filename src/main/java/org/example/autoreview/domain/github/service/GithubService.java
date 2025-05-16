package org.example.autoreview.domain.github.service;

import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.github.dto.request.GithubCodePushRequestDto;
import org.example.autoreview.domain.github.dto.request.GithubCodeRequestDto;
import org.example.autoreview.domain.github.dto.request.GithubTokenRequestDto;
import org.example.autoreview.domain.github.entity.GithubToken;
import org.example.autoreview.domain.github.entity.GithubTokenRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHFileNotFoundException;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
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
    private final String REPO_NAME = "Ori";

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

    //TODO: language와 github에서 인식하는 파일 형식이 달라서 매핑해야함
    @Transactional
    public void pushToGithub(String email, GithubCodePushRequestDto requestDto) throws IOException {
        String codePath = requestDto.title() + "[" + requestDto.language() + "]" + "/code." + requestDto.language();
        String readmePath = requestDto.title() + "[" + requestDto.language() + "]" + "/description.md";

        String githubToken = githubTokenRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_GITHUB_TOKEN)
        ).getGithubToken();

        GitHub github = new GitHubBuilder().withOAuthToken(githubToken).build();
        GHRepository repo = getOrCreateRepository(github, getGithubName(github));

        pushFile(repo, codePath, requestDto.code(), "Initial commit: code", "Updated code");
        pushFile(repo, readmePath, requestDto.description(), "Initial commit: description", "Updated README");

    }

    private GHRepository getOrCreateRepository(GitHub github, String username) throws IOException {
        try {
            return github.getRepository(username + "/" + REPO_NAME);
        } catch (GHFileNotFoundException e) {
            return github.createRepository(REPO_NAME)
                    .description("Auto-created ori repo")
                    .private_(false)
                    .create();
        }
    }

    private void pushFile(GHRepository repo, String path, String content, String initialMsg, String updateMsg) throws IOException {
        try {
            GHContent existing = repo.getFileContent(path, "main");
            existing.update(content, updateMsg, "main");
        } catch (GHFileNotFoundException e) {
            repo.createContent()
                    .path(path)
                    .message(initialMsg)
                    .content(content)
                    .commit();
        }
    }

    private String getGithubName(GitHub github) throws IOException {
        return github.getMyself().getLogin();
    }

}
