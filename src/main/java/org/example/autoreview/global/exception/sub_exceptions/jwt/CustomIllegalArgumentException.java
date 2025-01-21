package org.example.autoreview.global.exception.sub_exceptions.jwt;

import lombok.Getter;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;

@Getter
public class CustomIllegalArgumentException extends CustomRuntimeException {
    public CustomIllegalArgumentException(ErrorCode errorCode){super(errorCode);}
}
