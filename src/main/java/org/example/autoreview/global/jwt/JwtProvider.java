package org.example.autoreview.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.ForbiddenException;
import org.example.autoreview.global.exception.sub_exceptions.jwt.CustomExpiredJwtException;
import org.example.autoreview.global.exception.sub_exceptions.jwt.CustomIllegalArgumentException;
import org.example.autoreview.global.exception.sub_exceptions.jwt.CustomInvalidException;
import org.example.autoreview.global.exception.sub_exceptions.jwt.CustomUnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    private  Key key;

    @Value("${jwt.accessTokenExpireTime}")
    private long accessTokenExpireTime;

    @Value("${jwt.refreshTokenExpireTime}")
    private long refreshTokenExpireTime;

    @Value("${jwt.secret}")
    private String secretKey;
    private static final String TOKEN_TYPE = "Bearer";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    private static final String AUTHORITIES_KEY = "auth";

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder()
                .decode(secretKey);// Base64로 인코딩된 값을 시크릿키 변수에 저장한 값을 디코딩하여 바이트 배열로 변환
        //* Base64 (64진법) : 바이너리(2진) 데이터를 문자 코드에 영향을 받지 않는 공통 ASCII문자로 표현하기 위해 만들어진 인코딩
        key = Keys.hmacShaKeyFor(
                bytes);//디코팅된 바이트 배열을 기반으로 HMAC-SHA 알고르즘을 사용해서 Key객체로 반환 , 이를 key 변수에 대입
    }

    // 액세스 토큰 발급
    public JwtDto generateToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date accessTokenExpiration = new Date(now + accessTokenExpireTime);

        // Authorities를 Claim에 넣을 수 있도록 String으로 변경 (authority1,authority2,..)
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

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

        return  JwtDto.builder()
                .accessToken(BEARER_PREFIX + accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null){
            throw new ForbiddenException(ErrorCode.FORBIDDEN_JWT);
        }

        Collection<? extends GrantedAuthority> authorities = getAuthorities(claims);

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public List<SimpleGrantedAuthority> getAuthorities(Claims claims){
        return Collections.singletonList(new SimpleGrantedAuthority(
                claims.get(AUTHORITIES_KEY).toString()));
    }

    public void validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);

        } catch (ExpiredJwtException e){
            throw new CustomExpiredJwtException(ErrorCode.EXPIRED_TOKEN);
        } catch (SecurityException | MalformedJwtException e){
            throw new CustomInvalidException(ErrorCode.INVALID_TOKEN);
        } catch (UnsupportedJwtException e){
            throw new CustomUnsupportedJwtException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e){
            throw new CustomIllegalArgumentException(ErrorCode.NOT_FOUND_TOKEN);
        }
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
        if (StringUtils.hasText(accessToken) &&
                accessToken.startsWith(BEARER_PREFIX)) {
            return accessToken.substring(7);
        }
        return null;
    }
}
