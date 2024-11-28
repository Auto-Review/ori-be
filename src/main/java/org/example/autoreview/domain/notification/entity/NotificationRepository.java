package org.example.autoreview.domain.notification.entity;

import java.util.List;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("SELECT n FROM Notification n WHERE n.member.id = :memberId")
    List<Notification> findAllByMemberId(@Param("memberId") Long memberId);
}
