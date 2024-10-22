package org.example.autoreview.domain.notification.domain;

import java.util.List;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findAllByStatus(NotificationStatus status);

    @Query("SELECT n FROM Notification n WHERE n.member.email = :email")
    List<Notification> findAllByEmail(@Param("email") String email);
}
