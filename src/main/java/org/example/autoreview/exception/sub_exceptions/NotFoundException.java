package org.example.autoreview.exception.sub_exceptions;

import lombok.Getter;
import org.example.autoreview.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.exception.errorcode.ErrorCode;

@Getter
public class NotFoundException extends CustomRuntimeException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
