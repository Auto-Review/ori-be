package org.example.autoreview.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.sociallogin.LoginService;
import org.example.autoreview.domain.member.sociallogin.jwt.JwtDto;
import org.example.autoreview.domain.member.sociallogin.jwt.refresh.service.RefreshTokenService;
import org.example.autoreview.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
public class IndexController {

    private final LoginService loginService;

    private final RefreshTokenService refreshTokenService;

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

    @PostMapping("/refresh")
    public ApiResponse<?> confirmToken(@RequestParam(name = "email") String email){
        log.info("current user's refreshToken is {}", refreshTokenService.getRefreshToken(email));
        return ApiResponse.success(HttpStatus.OK, "ok");
    }

    @GetMapping("/reissued")
    public ApiResponse<?> reissuedToken(@RequestHeader String accessToken,
                                        @RequestHeader String refreshToken,
                                        HttpServletResponse response){



        return ApiResponse.success(HttpStatus.OK, "ok");
    }
}
