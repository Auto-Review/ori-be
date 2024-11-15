package org.example.autoreview.domain.fcm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.fcm.dto.request.FcmTokenSaveRequestDto;
import org.example.autoreview.domain.fcm.entity.FcmTokenRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;

    @Transactional
    public Long save(FcmTokenSaveRequestDto requestDto, Member member) {
        return requestDto.toEntity(member).getId();
    }

}
