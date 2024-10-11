package org.example.autoreview.domain.scheduler.domain;

import org.example.autoreview.domain.scheduler.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findAllByStatus(NotificationStatus status);
}
