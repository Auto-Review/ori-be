package org.example.autoreview.domain.fcm.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.fcm.entity.FcmTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class FcmTokenCommand {

    private final FcmTokenRepository fcmTokenRepository;

    @Transactional
    public Long save(FcmToken fcmToken) {
        return fcmTokenRepository.save(fcmToken).getId();
    }

    @Transactional
    public void fcmTokensUpdate(Map<FcmToken, LocalDate> fcmTokenMap) {
        for (Map.Entry<FcmToken, LocalDate> entry : fcmTokenMap.entrySet()) {
            entry.getKey().updateDate(entry.getValue());
        }
    }
}
