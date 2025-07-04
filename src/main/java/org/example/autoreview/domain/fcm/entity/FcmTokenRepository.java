package org.example.autoreview.domain.fcm.entity;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    @Query("SELECT f FROM FcmToken f WHERE f.member.id = :memberId AND f.token = :token")
    FcmToken findByTokenAndMember(@Param("token") String token, @Param("memberId") Long memberId);

}
