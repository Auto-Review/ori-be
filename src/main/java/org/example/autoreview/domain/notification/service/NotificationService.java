package org.example.autoreview.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.domain.NotificationRepository;
import org.example.autoreview.domain.notification.dto.request.NotificationSaveRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
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
    public Long save(Member member, NotificationSaveRequestDto requestDto) {
        Notification notification = requestDto.toEntity(member);
        return notificationRepository.save(notification).getId();
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
}
