package org.example.autoreview.domain.member.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.exception.errorcode.ErrorCode;
import org.example.autoreview.exception.sub_exceptions.ForbiddenException;
import org.example.autoreview.exception.sub_exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;

    @Value("${jwt.accessTokenExpireTime}")
    private long accessTokenExpireTime;

    @Value("${jwt.refreshTokenExpireTime}")
    private long refreshTokenExpireTime;

    private static final String TOKEN_TYPE = "Bearer";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    private static final String AUTHORITIES_KEY = "auth";

    public JwtProvider(@Value("${jwt.secret}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtDto generateToken(Authentication authentication){

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date accessTokenExpiration = new Date(now + accessTokenExpireTime);
        String accessToken = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .expiration(accessTokenExpiration)
                .signWith(key)
                .compact();

        String refreshToken = Jwts.builder()
                .expiration(new Date(now + refreshTokenExpireTime))
                .signWith(key)
                .compact();

        return JwtDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 액세스 토큰 발급
    public String generateAccessToken(OAuth2User oAuth2User) {
        long now = (new Date()).getTime();
        Date accessTokenExpiration = new Date(now + accessTokenExpireTime);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Authorities를 Claim에 넣을 수 있도록 String으로 변경 (authority1,authority2,..)
        String authorities = oAuth2User.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject((String) attributes.get("nickname"))
                .claim("auth", authorities)
                .expiration(accessTokenExpiration)
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null){
            throw new ForbiddenException(ErrorCode.JWT_FORBIDDEN);
        }

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public List<SimpleGrantedAuthority> getAuthorities(Claims claims){
        return Collections.singletonList(new SimpleGrantedAuthority(
                claims.get(AUTHORITIES_KEY).toString()));
    }

    public long getExpiration(String accessToken){
        Claims claims = parseClaims(accessToken);
        Date expiration = claims.getExpiration();
        long now = (new Date()).getTime();
        return expiration.getTime() - now;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e){
            log.warn("invalid JWT", e);
        } catch (ExpiredJwtException e){
            log.info("Expired JWT", e);
        } catch (UnsupportedJwtException e){
            log.warn("Unsupported JWT", e);
        } catch (IllegalArgumentException e){
            log.error("JWT claims string is empty", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken){
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String resolveAccessToken(String accessToken){
        if(StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)){
            return accessToken.substring(7);
        }
        return null;
    }

    public String resolveRefreshToken(String refreshToken){
        if(StringUtils.hasText(refreshToken)){
            return refreshToken;
        }
        return null;
    }
}
