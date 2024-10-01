package org.example.autoreview.exception;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.exception.errorcode.ErrorCode;
import org.example.autoreview.exception.response.ErrorResponse;
import org.example.autoreview.exception.sub_exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(final IllegalArgumentException ex) {
        log.warn("handleIllegalArgument", ex);
        final ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorResponse> handleServletException(final ServletException ex){
        log.warn("handleServletException", ex);
        final ErrorCode errorCode = ErrorCode.RESOURCE_NOT_FOUND;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException ex) {
        log.error("handleRuntimeException", ex);
        final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException ex) {
        log.error("handleNotFoundException", ex);
        return handleExceptionInternal(ex.getErrorCode());
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode));
    }

}
