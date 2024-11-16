package org.example.autoreview.domain.fcm.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.member.entity.Member;

@Getter
@NoArgsConstructor
public class FcmTokenSaveRequestDto {

    private String fcmToken;

    public FcmToken toEntity(Member member) {
        return FcmToken.builder()
                .token(fcmToken)
                .member(member)
                .build();
    }
}
