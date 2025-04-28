package org.example.autoreview.domain.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.fcm.dto.request.FcmTokenSaveRequestDto;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.fcm.entity.FcmTokenRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;

    @Transactional(readOnly = false)
    public Long save(FcmTokenSaveRequestDto requestDto, Member member) {
        FcmToken fcmToken = requestDto.toEntity(member);
        return fcmTokenRepository.save(fcmToken).getId();
    }

    public void pushNotification(List<FcmToken> fcmTokens, String title, String content) {
        log.info("pushNotification 트랜잭션 존재 여부: {}", TransactionSynchronizationManager.isActualTransactionActive());
        for (FcmToken fcmToken : fcmTokens) { CompletableFuture.runAsync(() -> {
            try {
                Message message = Message.builder()
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(content)
                                .build())
                        .setToken(fcmToken.getToken())
                        .build();

                FirebaseMessaging.getInstance().send(message);
                fcmToken.updateDate();

            } catch (FirebaseMessagingException e) {
                log.error("Failed to send message to device {}: {}", fcmToken.getId(), e.getMessage());
            } catch (Exception e) {
                log.error("An unexpected error occurred: {}", e.getMessage());
            }});
        }
    }
}
