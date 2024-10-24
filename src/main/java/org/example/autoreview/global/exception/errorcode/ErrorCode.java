package org.example.autoreview.global.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // COMMON
    INVALID_PARAMETER(400, HttpStatus.BAD_REQUEST, "잘못된 매개변수가 포함되었습니다."),
    RESOURCE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "자원을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),

    // SECURITY
    SECURITY_UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "자격 증명에 실패했습니다."),
    SECURITY_FORBIDDEN(403, HttpStatus.FORBIDDEN, "해당 페이지에 대한 권한이 없습니다."),

    // JWT
    JWT_FORBIDDEN(403, HttpStatus.UNAUTHORIZED, "권한 정보가 없는 JWT 토큰입니다."),
    // filter response 시 한글 깨짐으로 인한 영문 message 작성
    EXPIRED_TOKEN(403, HttpStatus.UNAUTHORIZED, "Expired Token"),
    INVALID_TOKEN(404, HttpStatus.NOT_FOUND, "Invalid Token"),
    UNSUPPORTED_TOKEN(404, HttpStatus.NOT_FOUND, "Unsupported Token"),
    TOKEN_IS_EMPTY(404, HttpStatus.NOT_FOUND, "Token Is Empty"),
    UNAUTHORIZED_TOKEN(403, HttpStatus.UNAUTHORIZED, "Unauthorized Token.."),
    UNMATCHED_TOKEN(401, HttpStatus.UNAUTHORIZED, "RefreshToken is not matched"),
    NOMATCHED_EMIAL(404, HttpStatus.NOT_FOUND, "Can't find Email"),

    // MEMBER
    MEMBER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),

    // POST
    POST_NOT_FOUND(404, HttpStatus.NOT_FOUND, "해당 포스트를 찾을 수 없습니다."),
    ;

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;

}
