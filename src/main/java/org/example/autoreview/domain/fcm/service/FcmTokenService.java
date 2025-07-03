package org.example.autoreview.domain.fcm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.fcm.dto.request.FcmTokenRequestDto;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmTokenService {

    private final MemberCommand memberCommand;
    private final FcmTokenCommand fcmTokenCommand;

    // 토큰 단위 락 (메모리 기준)
    private final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    public Long save(FcmTokenRequestDto requestDto, String email) {
        ReentrantLock lock = lockMap.computeIfAbsent(requestDto.getFcmToken(), e -> new ReentrantLock());
        lock.lock();

        try {
            Member member = memberCommand.findByEmail(email);
            FcmToken fcmToken = requestDto.toEntity(member);
            return fcmTokenCommand.save(fcmToken);
        } finally {
            lock.unlock();
        }
    }

    public Long delete(FcmTokenRequestDto requestDto, String email) {
        Member member = memberCommand.findByEmail(email);
        return fcmTokenCommand.delete(requestDto.getFcmToken(), member.getId());
    }
}
