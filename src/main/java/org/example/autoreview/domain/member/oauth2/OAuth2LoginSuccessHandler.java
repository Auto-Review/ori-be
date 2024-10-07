package org.example.autoreview.domain.member.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.domain.member.jwt.JwtDto;
import org.example.autoreview.domain.member.jwt.JwtProvider;
import org.example.autoreview.domain.member.jwt.refresh.RefreshToken;
import org.example.autoreview.domain.member.jwt.refresh.RefreshTokenRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        addJwtToHeader(oAuth2User, response);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
        String redirectionUrl = uriBuilder
                .queryParam("loginSuccess", true)
                .build()
                .toUriString();

        response.sendRedirect(redirectionUrl);
    }

    private void addJwtToHeader(OAuth2User oAuth2User, HttpServletResponse response){

        log.info("Sanding Token process start");
        JwtDto token = jwtProvider.generateToken(oAuth2User);

        RefreshToken redis = new RefreshToken(token.getRefreshToken(),
                (String) oAuth2User.getAttributes().get("nickname"));
        refreshTokenRepository.save(redis);

        response.setHeader("accessToken", token.getAccessToken());
        response.setHeader("refreshToken", token.getRefreshToken());
        log.info("Sanding Token process end");
    }
}
