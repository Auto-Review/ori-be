package org.example.autoreview.domain.refresh;

import java.util.concurrent.TimeUnit;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private String email;
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public RefreshToken(String email, String refreshToken, Long expiration){
        this.email = email;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
