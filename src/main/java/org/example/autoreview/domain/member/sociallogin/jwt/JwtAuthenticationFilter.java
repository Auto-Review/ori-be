package org.example.autoreview.domain.member.sociallogin.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.exception.errorcode.ErrorCode;
import org.example.autoreview.exception.response.ApiResponse;
import org.example.autoreview.exception.sub_exceptions.jwt.CustomExpiredJwtException;
import org.example.autoreview.exception.sub_exceptions.jwt.CustomIllegalArgumentException;
import org.example.autoreview.exception.sub_exceptions.jwt.CustomInvalidException;
import org.example.autoreview.exception.sub_exceptions.jwt.CustomUnsupportedJwtException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        if(StringUtils.hasText(jwt)){
            try {
                jwtProvider.validateToken(jwt);
                Authentication authentication = jwtProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (CustomExpiredJwtException | CustomIllegalArgumentException |
                     CustomInvalidException | CustomUnsupportedJwtException e) {

                setErrorResponse(response, e.getErrorCode());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode){
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(
                    ApiResponse.fail(errorCode)
            ));
        }catch (IOException e){
            log.error("An error occurred while writing the response: {}", e.getMessage());
        }
    }
}
