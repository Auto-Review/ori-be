package org.example.autoreview.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.notification.domain.Notification;
import org.example.autoreview.domain.notification.dto.request.NotificationSaveRequestDto;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationMemberService {

    private final NotificationService notificationService;
    private final MemberService memberService;

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

    // 알림 엔티티 모두 확인
    // 복습일이 오늘과 같고 Status 가 PENDING 인 알림만 전송하기
    // 전송 후 Status -> COMPLETE 로 변경
    @Transactional
    public void sendNotification() {
        // 여기에서 알림을 전송하는 로직을 구현합니다.
        List<Notification> notificationList = notificationService.findEntityAll();
        for (Notification notification : notificationList) {
            if (notification.getStatus().equals(NotificationStatus.PENDING)) {
                if (notification.getExecuteTime().isEqual(LocalDate.now())) {
                    notification.notificationStatusUpdate();
                    System.out.println("대상: " + notification.getMember().getEmail() + " / 알림 발송: " + notification.getContent() + " / 시간: " + LocalDateTime.now());
                }
            }
        }

    }


}
