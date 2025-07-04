package org.example.autoreview.domain.fcm.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.member.entity.Member;

@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "uq_fcm_token",
        columnNames = {"token"})}
)
@Entity
public class FcmToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Column(nullable = false, unique = true)
    private String token;

    private LocalDate lastUsedDate;

    @Builder
    public FcmToken(Member member, String token) {
        this.member = member;
        this.token = token;
        this.lastUsedDate = LocalDate.now();
    }

    public void updateDate(LocalDate newDate) {
        this.lastUsedDate = newDate;
    }
}
