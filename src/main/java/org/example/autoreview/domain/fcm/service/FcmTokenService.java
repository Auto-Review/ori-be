package org.example.autoreview.domain.fcm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.fcm.dto.request.FcmTokenSaveRequestDto;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmTokenService {

    private final MemberCommand memberCommand;
    private final FcmTokenCommand fcmTokenCommand;

    public Long save(FcmTokenSaveRequestDto requestDto, String email) {
        Member member = memberCommand.findByEmail(email);
        FcmToken fcmToken = requestDto.toEntity(member);
        return fcmTokenCommand.save(fcmToken);
    }
}
