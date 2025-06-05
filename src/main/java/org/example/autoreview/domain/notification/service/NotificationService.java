package org.example.autoreview.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.fcm.service.FcmTokenCommand;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.entity.Notification;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationCommand notificationCommand;
    private final MemberCommand memberCommand;
    private final FcmTokenCommand fcmTokenCommand;

    /**
     * 알림 전체 조회하는 메서드이다.
     */
    public List<NotificationResponseDto> findAll() {
        return notificationCommand.findAll().stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 사용자가 설정한 모든 알림을 조회하는 메서드이다.
     */
    public List<NotificationResponseDto> findAllByMemberId(String email) {
        Long memberId = memberCommand.findByEmail(email).getId();
        return notificationCommand.findAllByMemberId(memberId).stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 날짜에 사용자가 설정해놓은 알림 전체 조회하는 메서드이다.
     */
    public List<NotificationResponseDto> findAllByDate(String email, int year, int month) {
        Long memberId = memberCommand.findByEmail(email).getId();
        return notificationCommand.findAllByDate(memberId,year,month);
    }

    /**
     * 사용자가 읽지 않은 알림을 전체 조회하는 메서드이다.
     */
    public List<NotificationResponseDto> findAllNotificationIsNotCheckedByMemberId(String email) {
        LocalDate now = LocalDate.now();
        Long memberId = memberCommand.findByEmail(email).getId();
        return notificationCommand.findAllNotificationIsNotCheckedByMemberId(memberId, now).stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 알림을 읽음 상태로 변경해주는 메서드이다.
     */
    public void stateUpdate(Long id) {
        Notification notification = notificationCommand.findById(id);
        notificationCommand.readNotification(notification);
    }

    /**
     * FCM으로 전송할 알림을 요청하는 외부 API이다.
     */
    public void sendNotification() {
        LocalDate today = LocalDate.now();
        List<Notification> notificationList = notificationCommand.getNotifications();

        //
        for (Notification notification : notificationList) {
            if (notification.getStatus().equals(NotificationStatus.PENDING) && notification.getExecuteTime().isEqual(today)) {
                notificationCommand.updateStatus(notification);
                List<FcmToken> fcmTokens = notification.getMember().getFcmTokens();
                pushNotification(fcmTokens, notification.getTitle(), notification.getContent());
            }
        }
    }

    private void pushNotification(List<FcmToken> fcmTokens, String title, String content) {
        log.info("pushNotification 트랜잭션 존재 여부: {}", TransactionSynchronizationManager.isActualTransactionActive());

        Map<FcmToken, LocalDate> fcmTokenMap = new ConcurrentHashMap<>();
        for (FcmToken fcmToken : fcmTokens) {
            CompletableFuture.runAsync(() -> {
                try {
                    Message message = Message.builder()
                            .putData("title", title)
                            .putData("body", content)
                            .setToken(fcmToken.getToken())
                            .build();

                    FirebaseMessaging.getInstance().send(message);
                    fcmTokenMap.put(fcmToken, LocalDate.now());

                } catch (FirebaseMessagingException e) {
                    log.error("Failed to send message to device {}: {}", fcmToken.getId(), e.getMessage());
                } catch (Exception e) {
                    log.error("An unexpected error occurred: {}", e.getMessage());
                }
            });
        }
        fcmTokenCommand.fcmTokensUpdate(fcmTokenMap);
    }

    @Transactional
    public void deleteCompleteNotification() {
        List<Notification> completedNotifications = new ArrayList<>();
        List<Notification> notificationList = notificationCommand.findAll();

        for (Notification notification : notificationList) {
            if(notification.getStatus().equals(NotificationStatus.COMPLETE)) {
                completedNotifications.add(notification);
            }
        }
        notificationCommand.deleteAll(completedNotifications);
    }

}
