package org.example.autoreview.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.fcm.service.FcmTokenService;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.dto.request.NotificationSaveRequestDto;
import org.example.autoreview.domain.notification.dto.request.NotificationUpdateRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationDtoService {

    private final NotificationService notificationService;
    private final MemberService memberService;
    private final FcmTokenService fcmTokenService;

    public void save(String email, NotificationSaveRequestDto requestDto) {
        Member member = memberService.findByEmail(email);
        notificationService.save(member, requestDto);
    }

    @Transactional
    public void update(String email, NotificationUpdateRequestDto requestDto) {
        Notification notification = notificationService.findEntityById(requestDto.getId());
        userValidator(email, notification);
        notification.update(requestDto);
    }

    @Transactional
    public void delete(String email, Long notificationId) {
        Notification notification = notificationService.findEntityById(notificationId);
        userValidator(email,notification);
        notification.notificationStatusUpdate();
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

    private static void userValidator(String email, Notification notification) {
        if (!notification.getMember().getEmail().equals(email)) {
            throw new BadRequestException(ErrorCode.UNMATCHED_EMAIL);
        }
    }
}
