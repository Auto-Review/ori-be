package org.example.autoreview.domain.member.sociallogin;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.common.service.TokenVerifierService;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.member.sociallogin.jwt.JwtDto;
import org.example.autoreview.domain.member.sociallogin.jwt.JwtProvider;
import org.example.autoreview.domain.member.sociallogin.jwt.refresh.RefreshToken;
import org.example.autoreview.domain.member.sociallogin.jwt.refresh.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
