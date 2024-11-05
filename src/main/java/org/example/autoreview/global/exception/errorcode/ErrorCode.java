package org.example.autoreview.global.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // COMMON
    INVALID_PARAMETER(400, HttpStatus.BAD_REQUEST, "잘못된 매개변수가 포함되었습니다."),
    NOT_FOUND_RESOURCE(404, HttpStatus.NOT_FOUND, "자원을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),

    // SECURITY
    UNAUTHORIZED_SECURITY(401, HttpStatus.UNAUTHORIZED, "자격 증명에 실패했습니다."),
    FORBIDDEN_SECURITY(403, HttpStatus.FORBIDDEN, "해당 페이지에 대한 권한이 없습니다."),

    // JWT
    FORBIDDEN_JWT(403, HttpStatus.UNAUTHORIZED, "권한 정보가 없는 JWT 토큰입니다."),
    // filter response 시 한글 깨짐으로 인한 영문 message 작성
    EXPIRED_TOKEN(401, HttpStatus.UNAUTHORIZED, "Expired Token"),
    INVALID_TOKEN(401, HttpStatus.UNAUTHORIZED, "Invalid Token"),
    UNSUPPORTED_TOKEN(401, HttpStatus.UNAUTHORIZED, "Unsupported Token"),
    NOT_FOUND_TOKEN(401, HttpStatus.UNAUTHORIZED, "Token Is Empty"),
    UNAUTHORIZED_TOKEN(401, HttpStatus.UNAUTHORIZED, "Unauthorized Token.."),
    UNMATCHED_TOKEN(401, HttpStatus.UNAUTHORIZED, "RefreshToken is not matched"),

    NOT_FOUND_REFRESH_TOKEN(404, HttpStatus.NOT_FOUND, "RefreshToken을 찾을 수 없습니다."),

    // MEMBER
    NOT_FOUND_MEMBER(404, HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),

    // POST
    NOT_FOUND_POST(404, HttpStatus.NOT_FOUND, "해당 포스트를 찾을 수 없습니다."),

    // REVIEW
    NOT_FOUND_REVIEW(404, HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다."),

    // TIL_POST
    UNMATCHED_EMAIL(400, HttpStatus.BAD_REQUEST, "잘못된 사용자 접근입니다."),
    ;

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;

}
