package org.example.autoreview.domain.notification.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("SELECT n FROM Notification n WHERE n.member.id = :memberId")
    List<Notification> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT n FROM Notification n WHERE (n.member.id = :memberId AND n.isChecked = false AND n.executeTime <= :now)")
    List<Notification> findAllNotificationIsNotCheckedByMemberId(@Param("memberId") Long memberId, @Param("now") LocalDate now);
}
