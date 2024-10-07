package org.example.autoreview.exception.sub_exceptions;

import lombok.Getter;
import org.example.autoreview.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.exception.errorcode.ErrorCode;

@Getter
public class UnauthorizedException extends CustomRuntimeException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
