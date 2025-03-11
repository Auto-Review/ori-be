package org.example.autoreview.global.exception;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.response.ApiResponse;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.example.autoreview.global.exception.sub_exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public <T> ApiResponse<T> handleIllegalArgument(final IllegalArgumentException ex) {
        log.warn("handleIllegalArgument", ex);
        final ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ServletException.class)
    public <T> ApiResponse <T> handleServletException(final ServletException ex){
        log.warn("handleServletException", ex);
        final ErrorCode errorCode = ErrorCode.NOT_FOUND_RESOURCE;
        return handleExceptionInternal(errorCode);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class})
    public <T> ApiResponse <T> handleRuntimeException(final RuntimeException ex) {
        log.error("handleRuntimeException", ex);
        final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public <T> ApiResponse <T> handleNotFoundException(final NotFoundException ex) {
        log.error("handleNotFoundException", ex);
        return handleExceptionInternal(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public <T> ApiResponse <T> handleBadRequestException(final BadRequestException ex) {
        log.error("handleNotFoundException", ex);
        return handleExceptionInternal(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public <T> ApiResponse <T> handleUnauthorizedException(final UnauthorizedException ex) {
        log.error("handleUnauthorizedException", ex);
        return handleExceptionInternal(ex.getErrorCode());
    }

    private <T> ApiResponse <T> handleExceptionInternal(final ErrorCode errorCode) {
        return ApiResponse.fail(errorCode);
    }
}
