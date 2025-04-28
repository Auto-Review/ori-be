package org.example.autoreview.global.exception.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

@Schema(description = "API 응답")
public record ApiResponse<T>(
        @Schema(description = "HTTP 상태 코드", example = "OK")
        HttpStatus status,

        @Schema(description = "메시지")
        String message)
{
    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getHttpStatus(), errorCode.getMessage());
    }
}
