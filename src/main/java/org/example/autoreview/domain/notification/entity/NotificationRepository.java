package org.example.autoreview.domain.notification.entity;

import java.time.LocalDate;
import java.util.List;
import org.example.autoreview.domain.notification.dto.response.NotificationResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("SELECT n FROM Notification n WHERE n.member.id = :memberId")
    List<Notification> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT new org.example.autoreview.domain.notification.dto.response.NotificationResponseDto(n) FROM Notification n "
            + "WHERE n.member.id = :memberId AND FUNCTION('YEAR',n.executeTime) = :year AND FUNCTION('MONTH', n.executeTime) = :month")
    List<NotificationResponseDto> findAllByDate(@Param("memberId") Long memberId, @Param("year") int year, @Param("month") int month);

    @Query("SELECT n FROM Notification n WHERE (n.member.id = :memberId AND n.isChecked = false AND n.executeTime <= :now)")
    List<Notification> findAllNotificationIsNotCheckedByMemberId(@Param("memberId") Long memberId, @Param("now") LocalDate now);
}
