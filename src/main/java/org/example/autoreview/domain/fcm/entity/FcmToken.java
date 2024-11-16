package org.example.autoreview.domain.fcm.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.member.entity.Member;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class FcmToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    private String token;

    private LocalDate lastUsedDate;

    @Builder
    public FcmToken(Member member, String token) {
        this.member = member;
        this.token = token;
        this.lastUsedDate = LocalDate.now();
    }
}
