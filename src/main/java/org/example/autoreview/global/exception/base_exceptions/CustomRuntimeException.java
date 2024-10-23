package org.example.autoreview.global.exception.base_exceptions;

import lombok.Getter;
import org.example.autoreview.global.exception.errorcode.ErrorCode;

@Getter
public class CustomRuntimeException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomRuntimeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
