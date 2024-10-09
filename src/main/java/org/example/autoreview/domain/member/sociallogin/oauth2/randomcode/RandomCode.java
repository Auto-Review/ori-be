package org.example.autoreview.domain.member.sociallogin.oauth2.randomcode;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "randomCode", timeToLive = 1800)
public class RandomCode {

    @Id
    private String randomCode;
    private String nickname;

    public RandomCode(String randomCode, String nickname){
        this.randomCode = randomCode;
        this.nickname = nickname;
    }

}
