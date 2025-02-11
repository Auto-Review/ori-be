package org.example.autoreview.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.notification.entity.Notification;
import org.example.autoreview.domain.notification.entity.NotificationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationCommand {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<Notification> getNotifications() {
        // select
        log.info("getNotifications 트랜잭션 존재 여부: {}", TransactionSynchronizationManager.isActualTransactionActive());
        return notificationRepository.findAll();
    }

    @Transactional
    public void updateStatus(Notification notification) {
        // update
        log.info("updateStatus 트랜잭션 존재 여부: {}", TransactionSynchronizationManager.isActualTransactionActive());
        notification.statusUpdateToComplete();
    }
}
