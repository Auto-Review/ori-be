package org.example.autoreview.domain.member.sociallogin;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;

import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.jwt.AuthenticationJwtException;
import org.example.autoreview.global.exception.sub_exceptions.jwt.CustomInvalidException;
import org.example.autoreview.global.jwt.JwtDto;
import org.example.autoreview.global.jwt.JwtProvider;
import org.example.autoreview.domain.refresh.RefreshToken;
import org.example.autoreview.domain.refresh.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final TokenVerifierService tokenVerifierService;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.refreshTokenExpireTime}")
    private long freshTokenExpiration;

    @Transactional
    public JwtDto issuedToken(String accessToken) throws JsonProcessingException {

        Map<String, Object> payload = tokenVerifierService.validateGoogleAccessToken(accessToken);

        String email = (String) payload.get("email");

        memberService.saveOrFind(email);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, "");

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        JwtDto jwtDto = jwtProvider.generateToken(authentication);

        RefreshToken redis = new RefreshToken(email, jwtDto.getRefreshToken(), freshTokenExpiration);
        refreshTokenService.save(redis);

        return jwtDto;
    }

    public JwtDto reissue(String accessToken, String refreshToken) {
        log.info("current refreshToken value = {}", refreshToken);
        jwtProvider.validateToken(refreshToken);

        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        String getRefreshToken = refreshTokenService.getRefreshToken(authentication.getName());

        if (!getRefreshToken.equals(refreshToken)) {
            throw new AuthenticationJwtException(ErrorCode.UNMATCHED_TOKEN);
        }

        JwtDto newToken = jwtProvider.generateToken(authentication);

        RefreshToken redis = new RefreshToken(authentication.getName(), newToken.getRefreshToken(), freshTokenExpiration);
        refreshTokenService.save(redis);

        return newToken;
    }

}
