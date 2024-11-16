package org.example.autoreview.domain.fcm.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.fcm.dto.request.FcmTokenSaveRequestDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FcmTokenMemberService {

    private final MemberService memberService;
    private final FcmTokenService fcmTokenService;

    public Long save(FcmTokenSaveRequestDto requestDto, String email) {
        Member member = memberService.findByEmail(email);
        return fcmTokenService.save(requestDto, member);
    }
}
