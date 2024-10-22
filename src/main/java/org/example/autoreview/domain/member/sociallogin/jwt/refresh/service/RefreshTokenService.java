package org.example.autoreview.domain.member.sociallogin.jwt.refresh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.sociallogin.jwt.refresh.RefreshToken;
import org.example.autoreview.domain.member.sociallogin.jwt.refresh.RefreshTokenRepository;
import org.example.autoreview.exception.errorcode.ErrorCode;
import org.example.autoreview.exception.sub_exceptions.jwt.CustomInvalidException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private static final String REFRESH_PREFIX = "refreshToken:";

    public void save(RefreshToken refreshToken){
        refreshTokenRepository.save(refreshToken);
    }

    public String getRefreshToken(String email){

        log.info("received email {}", email);

        RefreshToken refreshToken = refreshTokenRepository.findByEmail(email).orElseThrow(() ->
                new CustomInvalidException(ErrorCode.INVALID_TOKEN));

        return refreshToken.getRefreshToken();
    }
}
