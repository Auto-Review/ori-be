package org.example.autoreview.global.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.Role;
import org.example.autoreview.domain.member.sociallogin.LoginService;
import org.example.autoreview.global.jwt.JwtDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomInitializer implements ApplicationRunner {

    private final LoginService loginService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member memberA = Member.builder()
                .email("abc@naver.com")
                .nickname("kim")
                .role(Role.USER)
                .build();

        JwtDto jwtDtoA = loginService.issuedTokenByEmail(memberA.getEmail());

        Member memberB = Member.builder()
                .email("ehgur@naver.com")
                .nickname("chl")
                .role(Role.USER)
                .build();

        JwtDto jwtDtoB = loginService.issuedTokenByEmail(memberB.getEmail());

        log.info("Test MemberA accessToken is {}", jwtDtoA.getAccessToken());
        log.info("Test MemberB accessToken is {}", jwtDtoB.getAccessToken());
    }
}
