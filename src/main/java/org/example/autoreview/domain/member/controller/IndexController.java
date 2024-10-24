package org.example.autoreview.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.sociallogin.LoginService;
import org.example.autoreview.global.jwt.JwtDto;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController("/v1/api/auth")
public class IndexController {

    private final LoginService loginService;

    // accessToken 하나만 있는데 requestBody 사용이 맞는가?
    @Operation(summary = "로그인 이후 토큰 발급", description = "토큰 발급")
    @PostMapping("/token")
    public ApiResponse<?> issuedToken(@RequestBody String accessToken, HttpServletResponse response) throws JsonProcessingException {
        log.info("client send {}", accessToken);

        JwtDto jwtDto = loginService.issuedToken(accessToken);

        log.info("accessToken = {}", jwtDto.getAccessToken());
        log.info("refreshToken = {}", jwtDto.getRefreshToken());

        response.setHeader("accessToken", jwtDto.getAccessToken());
        response.setHeader("refreshToken", jwtDto.getRefreshToken());
        return ApiResponse.success(HttpStatus.OK, "ok");
    }

    @GetMapping("/test")
    public ApiResponse<?> test(@RequestHeader(name = "Authorization") String accessToken){
        log.info("current accessToken = {}", accessToken);
        return ApiResponse.success(HttpStatus.OK, "ok");
    }

    @Operation(summary = "토큰 재발급", description = "토큰 재발급")
    @GetMapping("/reissued")
    public ApiResponse<?> reissuedToken(@RequestHeader(name = "Authorization") String accessToken,
                                        @RequestHeader(name = "refreshToken") String refreshToken,
                                        HttpServletResponse response){

        JwtDto jwtDto = loginService.reissue(accessToken, refreshToken);

        log.info("accessToken = {}", jwtDto.getAccessToken());
        log.info("refreshToken = {}", jwtDto.getRefreshToken());

        response.setHeader("accessToken", jwtDto.getAccessToken());
        response.setHeader("refreshToken", jwtDto.getRefreshToken());
        return ApiResponse.success(HttpStatus.OK, "ok");
    }
}
