package org.example.autoreview.domain.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.common.basetime.BaseEntity;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.scheduler.domain.Notification;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CodePost> codePosts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @Column(nullable = false)
    private String nickname;

    private Role role;

    @Builder
    public Member(String email, String nickname, Role role){
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public void addCodePost(CodePost codePost) {
        codePosts.add(codePost);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public Member update(String nickname){
        this.nickname = nickname;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
