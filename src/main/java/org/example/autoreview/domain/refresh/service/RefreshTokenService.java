package org.example.autoreview.domain.refresh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.refresh.RefreshToken;
import org.example.autoreview.domain.refresh.RefreshTokenRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void save(RefreshToken refreshToken){
        refreshTokenRepository.save(refreshToken);
    }

    public String getRefreshToken(String email){

        log.info("received email = {}", email);

        RefreshToken refreshToken = refreshTokenRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(ErrorCode.NOT_FOUND_REFRESH_TOKEN));

        return refreshToken.getRefreshToken();
    }
}
