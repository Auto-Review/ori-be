package org.example.autoreview.domain.member.sociallogin.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.sociallogin.TokenPrefix;
import org.example.autoreview.domain.member.sociallogin.oauth2.randomcode.RandomCode;
import org.example.autoreview.domain.member.sociallogin.oauth2.randomcode.RandomCodeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.SecureRandom;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RandomCodeRepository randomCodeRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String randomNumber = createRandomNumber();

        RandomCode redis = new RandomCode(TokenPrefix.RANDOMCODE.toString() + randomNumber,
                oAuth2User.getAttribute("nickname"));
        randomCodeRepository.save(redis);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
        String redirectionUrl = uriBuilder
                .queryParam("loginSuccess", true)
                .queryParam(randomNumber)
                .build()
                .toUriString();

        response.sendRedirect(redirectionUrl);
    }

    private String createRandomNumber() {
        int digits = 6;
        SecureRandom rand = new SecureRandom();
        StringBuilder randomNum = new StringBuilder();

        for (int i = 0; i < digits; i++) {
            String random = Integer.toString(rand.nextInt(10));
            randomNum.append(random);
        }

        return randomNum.toString();
    }
}
