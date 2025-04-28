package org.example.autoreview.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.entity.Notification;
import org.example.autoreview.domain.notification.entity.NotificationRepository;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void save(Member member, CodePost codePost) {
        Notification notification = Notification.builder()
                .title("ORI 복습 알림")
                .content(codePost.getTitle())
                .status(NotificationStatus.PENDING)
                .executeTime(codePost.getReviewDay())
                .member(member)
                .codePostId(codePost.getId())
                .build();

        notificationRepository.save(notification);
    }

    public boolean existsById(Long id) {
        return notificationRepository.existsById(id);
    }

    public Notification findEntityById(Long id) {
        return notificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_NOTIFICATION)
        );
    }

    public List<Notification> findEntityAll() {
        return notificationRepository.findAll();
    }

    public List<NotificationResponseDto> findAll() {
        return notificationRepository.findAll().stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<NotificationResponseDto> findAllByMemberId(Long memberId) {
        return notificationRepository.findAllByMemberId(memberId).stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<NotificationResponseDto> findAllNotificationIsNotCheckedByMemberId(Long memberId) {
        LocalDate now = LocalDate.now();
        List<Notification> notifications = notificationRepository.findAllNotificationIsNotCheckedByMemberId(memberId, now);

        return notifications.stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(String email, CodePost codePost, NotificationStatus status) {
        Notification notification = notificationRepository.findById(codePost.getId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_NOTIFICATION)
        );
        userValidator(email, notification);
        notification.update(codePost, status);
    }

    @Transactional
    public void delete(String email, Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_NOTIFICATION)
        );
        userValidator(email,notification);
        notificationRepository.delete(notification);
    }

    @Transactional
    public void deleteAll(List<Notification> completedNotifications) {
        notificationRepository.deleteAll(completedNotifications);
    }

    private static void userValidator(String email, Notification notification) {
        if (!notification.getMember().getEmail().equals(email)) {
            throw new BadRequestException(ErrorCode.UNMATCHED_EMAIL);
        }
    }

}
