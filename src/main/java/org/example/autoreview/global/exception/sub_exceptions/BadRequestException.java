package org.example.autoreview.global.exception.sub_exceptions;

import lombok.Getter;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;

@Getter
public class BadRequestException extends CustomRuntimeException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
