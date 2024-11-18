package org.example.autoreview.domain.notification.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.fcm.service.FcmTokenService;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.dto.request.NotificationSaveRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationDtoService {

    private final NotificationService notificationService;
    private final MemberService memberService;
    private final FcmTokenService fcmTokenService;

    public Long save(String email, NotificationSaveRequestDto requestDto) {
        Member member = memberService.findByEmail(email);
        return notificationService.save(member, requestDto);
    }

    public List<NotificationResponseDto> findAll() {
        return notificationService.findAll();
    }

    public List<NotificationResponseDto> findAllByMemberId(String email) {
        Long memberId = memberService.findByEmail(email).getId();
        return notificationService.findAllByMemberId(memberId);
    }

    @Transactional
    public void sendNotification() {
        List<Notification> notificationList = notificationService.findEntityAll();
        LocalDate today = LocalDate.now();

        for (Notification notification : notificationList) {
            List<FcmToken> fcmTokens = notification.getMember().getFcmTokens();

            if (notification.getStatus().equals(NotificationStatus.PENDING) && notification.getExecuteTime().isEqual(today)) {
                notification.notificationStatusUpdate();
                fcmTokenService.pushNotification(fcmTokens, notification.getTitle(), notification.getContent());
            }
        }
    }

    @Transactional
    public void deleteCompleteNotification() {
        List<Notification> completedNotifications = new ArrayList<>();
        List<Notification> notificationList = notificationService.findEntityAll();
        for (Notification notification : notificationList) {
            if(notification.getStatus().equals(NotificationStatus.COMPLETE)) {
                completedNotifications.add(notification);
            }
        }
        notificationService.deleteAll(completedNotifications);
    }


}
