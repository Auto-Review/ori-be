package org.example.autoreview.global.exception.sub_exceptions.jwt;

import lombok.Getter;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;

@Getter
public class AuthenticationJwtException extends CustomRuntimeException {
    public AuthenticationJwtException(ErrorCode errorCode){super(errorCode);}
}
