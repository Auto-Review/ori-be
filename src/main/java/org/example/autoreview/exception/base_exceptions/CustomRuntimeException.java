package org.example.autoreview.exception.base_exceptions;

import lombok.Getter;
import org.example.autoreview.exception.errorcode.ErrorCode;

@Getter
public class CustomRuntimeException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomRuntimeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
