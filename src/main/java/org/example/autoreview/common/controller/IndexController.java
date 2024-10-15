package org.example.autoreview.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.common.service.TokenVerifierService;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.member.sociallogin.jwt.JwtDto;
import org.example.autoreview.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RestController
public class IndexController {

    private final TokenVerifierService tokenVerifierService;

    private final MemberService memberService;

    @GetMapping("/")
    public String test(){
        return "Test";
    }

    @PostMapping("/auth/token")
    public ApiResponse<?> issuedToken(@RequestBody String accessToken, HttpServletResponse response) throws JsonProcessingException {
        log.info("client send {}", accessToken);

        Map<String, Object> payload  = tokenVerifierService.validateGoogleAccessToken(accessToken);
        log.info("email = {}", payload.get("email"));
//        JwtDto jwtDto = memberService.issuedToken(email);
//
//        log.info("accessToken = {}", jwtDto.getAccessToken());
//        log.info("refreshToken = {}", jwtDto.getRefreshToken());
//
//        response.setHeader("accessToken", jwtDto.getAccessToken());
//        response.setHeader("refreshToken", jwtDto.getRefreshToken());
        return ApiResponse.success(HttpStatus.OK, "ok");
    }
}
