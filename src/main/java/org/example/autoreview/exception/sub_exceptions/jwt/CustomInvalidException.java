package org.example.autoreview.exception.sub_exceptions.jwt;

import lombok.Getter;
import org.example.autoreview.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.exception.errorcode.ErrorCode;

@Getter
public class CustomInvalidException extends CustomRuntimeException {
    public CustomInvalidException(ErrorCode errorCode){super(errorCode);}
}
