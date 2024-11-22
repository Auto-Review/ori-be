package org.example.autoreview.global.jwt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class CustomUserDetails implements UserDetails {

    private final Member member;
    private String password;

    private CustomUserDetails(Member member, String encodePassword){
        this.member = member;
        this.password = encodePassword;
    }

    public static CustomUserDetails of(Member member, String encodePassword){
        return new CustomUserDetails(member, encodePassword);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(Role.values()).stream()
                .map(role -> new SimpleGrantedAuthority(role.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
