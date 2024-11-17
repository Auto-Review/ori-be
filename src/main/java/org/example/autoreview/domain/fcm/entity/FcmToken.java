package org.example.autoreview.domain.fcm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.member.entity.Member;

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

    public void updateDate() {
        this.lastUsedDate = LocalDate.now();
    }
}
