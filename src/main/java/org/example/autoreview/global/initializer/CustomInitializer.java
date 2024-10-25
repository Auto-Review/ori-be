package org.example.autoreview.global.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member member = Member.builder()
                .email("abc@naver.com")
                .nickname("kim")
                .role(Role.USER)
                .build();

        memberRepository.save(member);

        JwtDto jwtDto = loginService.issuedTokenByEmail(member.getEmail());
        log.info("Test Member accessToken is = {}", jwtDto.getAccessToken());
    }
}
