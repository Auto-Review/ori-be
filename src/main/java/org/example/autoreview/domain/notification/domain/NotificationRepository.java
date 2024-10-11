package org.example.autoreview.domain.notification.domain;

import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findAllByStatus(NotificationStatus status);
}
