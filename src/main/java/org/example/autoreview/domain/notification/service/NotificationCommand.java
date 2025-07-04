package org.example.autoreview.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.entity.Notification;
import org.example.autoreview.domain.notification.entity.NotificationRepository;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationCommand {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public boolean existsByCodePostId(Long id) {
        return notificationRepository.existsByCodePostId(id);
    }

    @Transactional(readOnly = true)
    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_NOTIFICATION)
        );
    }

    @Transactional(readOnly = true)
    public Notification findByCodePostId(Long codePostId) {
        return notificationRepository.findByCodePostId(codePostId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_NOTIFICATION)
        );
    }

    @Transactional(readOnly = true)
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Notification> findAllByMemberId(Long memberId) {
        return notificationRepository.findAllByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> findAllByDate(Long memberId, int year, int month) {
        return notificationRepository.findAllByDate(memberId,year,month);
    }

    @Transactional(readOnly = true)
    public List<Notification> findAllNotificationIsNotCheckedByMemberId(Long memberId, LocalDate now) {
        return notificationRepository.findAllNotificationIsNotCheckedByMemberId(memberId, now);
    }

    @Transactional
    public void update(Notification notification, CodePost codePost, NotificationStatus status) {
        notification.update(codePost, status);
    }

    @Transactional
    public void readNotification(Notification notification) {
        notification.readNotification();
    }

    @Transactional
    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }

    @Transactional
    public void deleteAll(List<Notification> completedNotifications) {
        notificationRepository.deleteAll(completedNotifications);
    }

    @Transactional(readOnly = true)
    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    @Transactional
    public void updateStatus(Notification notification) {
        notification.updateStatusToComplete();
    }
}
