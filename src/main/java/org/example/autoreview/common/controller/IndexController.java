package org.example.autoreview.common.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.member.sociallogin.TokenPrefix;
import org.example.autoreview.domain.member.sociallogin.jwt.JwtDto;
import org.example.autoreview.domain.member.sociallogin.jwt.JwtProvider;
import org.example.autoreview.domain.member.sociallogin.oauth2.randomcode.RandomCode;
import org.example.autoreview.domain.member.sociallogin.oauth2.randomcode.RandomCodeRepository;
import org.example.autoreview.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final RandomCodeRepository randomCodeRepository;
    private final MemberService memberService;

    @GetMapping("/")
    public String index(@RequestParam(name = "randomCode", required = false) String randomCode
            , Model model){

        if(randomCode != null){
            log.info("start randomcode");
            // test용 나중에 오류 코드 따로 생성할 것
            RandomCode code = randomCodeRepository.findById(TokenPrefix.RANDOMCODE.toString() + randomCode)
                    .orElseThrow(IllegalArgumentException::new);

            log.info(code.getEmail());
            model.addAttribute("email", code.getEmail());
        }

        return "index";
    }

    @ResponseBody
    @PostMapping("/auth/token")
    public ApiResponse<?> issuedToken(String email, HttpServletResponse response){
        JwtDto jwtDto = memberService.issuedToken(email);

        log.info("accessToken = {}", jwtDto.getAccessToken());
        log.info("refreshToken = {}", jwtDto.getRefreshToken());

        response.setHeader("accessToken", jwtDto.getAccessToken());
        response.setHeader("refreshToken", jwtDto.getRefreshToken());
        return ApiResponse.success(HttpStatus.OK, "ok");
    }
}
