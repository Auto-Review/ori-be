package org.example.autoreview.domain.notification.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.domain.NotificationRepository;
import org.example.autoreview.domain.notification.dto.request.NotificationSaveRequestDto;
import org.example.autoreview.domain.notification.dto.request.NotificationUpdateRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void save(Member member, CodePost codePost, NotificationSaveRequestDto requestDto) {
        Notification notification = requestDto.toEntity(member, codePost);
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
    public void update(String email, NotificationUpdateRequestDto requestDto) {
        Notification notification = notificationRepository.findById(requestDto.getId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_NOTIFICATION)
        );
        userValidator(email, notification);
        notification.update(requestDto);
    }

    @Transactional
    public void delete(String email, Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_NOTIFICATION)
        );
        userValidator(email,notification);
        notification.notificationStatusUpdate();
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
