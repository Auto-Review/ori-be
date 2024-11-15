package org.example.autoreview.domain.fcm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.fcm.entity.FcmToken;
import org.example.autoreview.domain.member.entity.Member;

@Getter
@RequiredArgsConstructor
public class FcmTokenSaveRequestDto {

    @Schema(description = "FCM에서 받아온 디바이스 토큰")
    private final String token;

    public FcmToken toEntity(Member member) {
        return FcmToken.builder()
                .token(token)
                .build();
    }
}
