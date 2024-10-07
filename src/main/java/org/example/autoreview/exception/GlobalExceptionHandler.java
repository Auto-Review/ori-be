package org.example.autoreview.exception;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.exception.errorcode.ErrorCode;
<<<<<<< HEAD
import org.example.autoreview.exception.response.ApiResponse;
import org.example.autoreview.exception.sub_exceptions.NotFoundException;
=======
import org.example.autoreview.exception.response.ErrorResponse;
import org.example.autoreview.exception.sub_exceptions.ForbiddenException;
import org.example.autoreview.exception.sub_exceptions.NotFoundException;
import org.example.autoreview.exception.sub_exceptions.UnauthorizedException;
import org.springframework.http.ResponseEntity;
>>>>>>> 6927f13 (feat: 초기 jwt 구현 완료)
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public <T> ApiResponse <T> handleIllegalArgument(final IllegalArgumentException ex) {
        log.warn("handleIllegalArgument", ex);
        final ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(ServletException.class)
    public <T> ApiResponse <T> handleServletException(final ServletException ex){
        log.warn("handleServletException", ex);
        final ErrorCode errorCode = ErrorCode.RESOURCE_NOT_FOUND;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler({RuntimeException.class})
    public <T> ApiResponse <T> handleRuntimeException(final RuntimeException ex) {
        log.error("handleRuntimeException", ex);
        final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(NotFoundException.class)
    public <T> ApiResponse <T> handleNotFoundException(final NotFoundException ex) {
        log.error("handleNotFoundException", ex);
        return handleExceptionInternal(ex.getErrorCode());
    }

<<<<<<< HEAD
    private <T> ApiResponse <T> handleExceptionInternal(final ErrorCode errorCode) {
        return ApiResponse.fail(errorCode);
=======
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final UnauthorizedException ex) {
        log.error("handleUnauthorizedException", ex);
        return handleExceptionInternal(ex.getErrorCode());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final ForbiddenException ex) {
        log.error("handleForbiddenException", ex);
        return handleExceptionInternal(ex.getErrorCode());
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode));
>>>>>>> 6927f13 (feat: 초기 jwt 구현 완료)
    }

}
