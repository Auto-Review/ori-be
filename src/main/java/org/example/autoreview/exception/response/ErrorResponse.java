package org.example.autoreview.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.autoreview.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;

    public static ErrorResponse of(final ErrorCode code){
        return new ErrorResponse(code.getStatusCode(), code.getHttpStatus(), code.getMessage());
    }

}
