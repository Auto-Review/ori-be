package org.example.autoreview.domain.member.sociallogin.jwt.refresh;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 14440)
public class RefreshToken {

    @Id
    private String refreshToken;
    private String nickname;

    public RefreshToken(String refreshToken, String nickname){
        this.refreshToken = refreshToken;
        this.nickname = nickname;
    }
}
