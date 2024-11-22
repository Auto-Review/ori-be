package org.example.autoreview.domain.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.fcm.dto.request.FcmTokenSaveRequestDto;
import org.example.autoreview.domain.fcm.entity.FcmToken;
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
        FcmToken fcmToken = requestDto.toEntity(member);
        return fcmTokenRepository.save(fcmToken).getId();
    }

    public void pushNotification(List<FcmToken> fcmTokens, String title, String content) {
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
