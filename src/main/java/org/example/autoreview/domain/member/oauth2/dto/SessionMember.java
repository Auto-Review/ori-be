package org.example.autoreview.domain.member.oauth2.dto;

import lombok.Getter;
import org.example.autoreview.domain.member.entity.Member;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private String nickname;
    private String email;

    public SessionMember(Member member){
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }
}
