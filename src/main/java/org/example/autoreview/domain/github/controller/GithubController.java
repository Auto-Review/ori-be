package org.example.autoreview.domain.github.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.github.dto.request.GithubCodePushRequestDto;
import org.example.autoreview.domain.github.dto.request.GithubCodeRequestDto;
import org.example.autoreview.domain.github.service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/github")
public class GithubController {

    private final GithubService githubService;

    @Operation(description = "깃헙 인증 토큰이 있는지 확인")
    @GetMapping("/token/check")
    public ResponseEntity<Boolean> checkToken(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(githubService.tokenCheck(userDetails.getUsername()));
    }

    @Operation(description = "깃헙 리다이렉트 된 일회성 인증 코드로 인증 토큰 발급 후 저장")
    @PostMapping("/callback")
    public ResponseEntity<Long> callbackAndSave(@RequestBody GithubCodeRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        String accessToken = githubService.getAccessToken(requestDto);
        return ResponseEntity.ok().body(githubService.save(accessToken, userDetails.getUsername()));
    }

    @Operation
    @PostMapping("/push/post/code")
    public ResponseEntity<String> push(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestBody GithubCodePushRequestDto requestDto) throws IOException {
        githubService.pushToGithub(userDetails.getUsername(), requestDto);
        return ResponseEntity.ok().body("Push to GitHub completed successfully.");
    }

}
