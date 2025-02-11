package org.example.autoreview.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.service.CodePostService;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.fcm.service.FcmTokenService;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.notification.dto.request.NotificationRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.entity.Notification;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
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
    private final CodePostService codePostService;
    private final MemberService memberService;
    private final FcmTokenService fcmTokenService;
    private final NotificationCommand notificationCommand;

    @Transactional
    public void saveOrUpdate(String email, NotificationRequestDto requestDto) {
        CodePost codePost = codePostService.findEntityById(requestDto.getCodePostId());
        Member member = memberService.findByEmail(email);

        if(notificationService.existsById(codePost.getId())) {
            notificationService.update(email, requestDto);
            return;
        }
        notificationService.save(member, codePost, requestDto);
    }

    public void delete(String email, Long id) {
        notificationService.delete(email, id);
    }

    public List<NotificationResponseDto> findAll() {
        return notificationService.findAll();
    }

    public List<NotificationResponseDto> findAllByMemberId(String email) {
        Long memberId = memberService.findByEmail(email).getId();
        return notificationService.findAllByMemberId(memberId);
    }

    public List<NotificationResponseDto> findAllNotificationIsNotCheckedByMemberId(String email) {
        Long memberId = memberService.findByEmail(email).getId();
        return notificationService.findAllNotificationIsNotCheckedByMemberId(memberId);
    }

    @Transactional
    public void update(Long id) {
        Notification notification = notificationService.findEntityById(id);
        notification.checkUpdateToTrue();
    }

    public void sendNotification() {
        LocalDate today = LocalDate.now();
        List<Notification> notificationList = notificationCommand.getNotifications();
        log.info("getNotificationList 트랜잭션 종료");

        for (Notification notification : notificationList) {
            if (notification.getStatus().equals(NotificationStatus.PENDING) && notification.getExecuteTime().isEqual(today)) {
                notificationCommand.updateStatus(notification);
                log.info("updateStatus 트랜잭션 종료");

                List<FcmToken> fcmTokens = notification.getMember().getFcmTokens();
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
