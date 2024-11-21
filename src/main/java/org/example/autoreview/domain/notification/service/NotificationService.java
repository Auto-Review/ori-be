package org.example.autoreview.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.domain.NotificationRepository;
import org.example.autoreview.domain.notification.dto.request.NotificationSaveRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void save(Member member, NotificationSaveRequestDto requestDto) {
        Notification notification = requestDto.toEntity(member);
        notificationRepository.save(notification);
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

    @Transactional
    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }

    @Transactional
    public void deleteAll(List<Notification> completedNotifications) {
        notificationRepository.deleteAll(completedNotifications);
    }

}
