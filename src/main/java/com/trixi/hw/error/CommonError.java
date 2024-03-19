package com.trixi.hw.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonError extends RuntimeException {

    private Enum<ValidationCode> error;
    private String scope;
    private String message;
    private HttpStatus httpStatus;

    public CommonError(Enum<ValidationCode> error, String scope, String message, HttpStatus httpStatus) {
        this.error = error;
        this.scope = scope;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
