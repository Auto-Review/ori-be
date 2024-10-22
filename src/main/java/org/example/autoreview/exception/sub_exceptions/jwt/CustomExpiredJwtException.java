package org.example.autoreview.exception.sub_exceptions.jwt;

import lombok.Getter;
import org.example.autoreview.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.exception.errorcode.ErrorCode;

@Getter
public class CustomExpiredJwtException extends CustomRuntimeException {
    public CustomExpiredJwtException(ErrorCode errorCode){super(errorCode);}
}
