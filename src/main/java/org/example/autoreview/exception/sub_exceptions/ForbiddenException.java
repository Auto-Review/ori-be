package org.example.autoreview.exception.sub_exceptions;

import lombok.Getter;
import org.example.autoreview.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.exception.errorcode.ErrorCode;

@Getter
public class ForbiddenException extends CustomRuntimeException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
