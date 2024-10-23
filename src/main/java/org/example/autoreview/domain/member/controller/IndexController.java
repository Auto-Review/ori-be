package org.example.autoreview.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.sociallogin.LoginService;
import org.example.autoreview.common.jwt.JwtDto;
import org.example.autoreview.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class IndexController {

    private final LoginService loginService;

    // accessToken 하나만 있는데 requestBody 사용이 맞는가?
    @PostMapping("/auth/token")
    public ApiResponse<?> issuedToken(@RequestBody String accessToken, HttpServletResponse response) throws JsonProcessingException {
        log.info("client send {}", accessToken);

        JwtDto jwtDto = loginService.issuedToken(accessToken);

        log.info("accessToken = {}", jwtDto.getAccessToken());
        log.info("refreshToken = {}", jwtDto.getRefreshToken());

        response.setHeader("accessToken", jwtDto.getAccessToken());
        response.setHeader("refreshToken", jwtDto.getRefreshToken());
        return ApiResponse.success(HttpStatus.OK, "ok");
    }

    @GetMapping("/api/test")
    public ApiResponse<?> test(@RequestHeader(name = "Authorization") String accessToken){
        log.info("current accessToken = {}", accessToken);
        return ApiResponse.success(HttpStatus.OK, "ok");
    }

    @GetMapping("/reissued")
    public ApiResponse<?> reissuedToken(@RequestHeader String accessToken,
                                        @RequestHeader String refreshToken,
                                        HttpServletResponse response){



        return ApiResponse.success(HttpStatus.OK, "ok");
    }
}
