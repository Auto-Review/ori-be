package org.example.autoreview.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.sociallogin.LoginDto;
import org.example.autoreview.domain.member.sociallogin.LoginService;
import org.example.autoreview.global.aspect.NoLogging;
import org.example.autoreview.global.jwt.JwtDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/auth")
@RestController
public class IndexController {

    private final LoginService loginService;

    @NoLogging
    // accessToken 하나만 있는데 requestBody 사용이 맞는가?
    @Operation(summary = "로그인 이후 토큰 발급", description = "토큰 발급")
    @PostMapping("/token")
    public ResponseEntity<String> issuedToken(@RequestBody String accessToken, HttpServletResponse response) throws JsonProcessingException {
        log.info("client send {}", accessToken);

        LoginDto loginDto = loginService.issuedToken(accessToken);
        JwtDto jwtDto = loginDto.getJwt();

        log.info("accessToken = {}", jwtDto.getAccessToken());
        log.info("refreshToken = {}", jwtDto.getRefreshToken());

        response.setHeader("accessToken", jwtDto.getAccessToken());
        response.setHeader("refreshToken", jwtDto.getRefreshToken());
        return ResponseEntity.ok().body(loginDto.getEmail());
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestHeader(name = "Authorization") String accessToken,
                                    @AuthenticationPrincipal UserDetails userDetails){
        log.info("current accessToken = {}", accessToken);
        log.info("current user = {}", userDetails.getUsername());
        return ResponseEntity.ok().body("ok");
    }

    @NoLogging
    @Operation(summary = "토큰 재발급", description = "토큰 재발급")
    @GetMapping("/reissued")
    public ResponseEntity<String> reissuedToken(@RequestHeader(name = "Authorization") String accessToken,
                                        @RequestHeader(name = "refreshToken") String refreshToken,
                                        HttpServletResponse response){

        JwtDto jwtDto = loginService.reissue(accessToken, refreshToken);

        log.info("accessToken = {}", jwtDto.getAccessToken());
        log.info("refreshToken = {}", jwtDto.getRefreshToken());

        response.setHeader("accessToken", jwtDto.getAccessToken());
        response.setHeader("refreshToken", jwtDto.getRefreshToken());
        return ResponseEntity.ok().body("ok");
    }
}
