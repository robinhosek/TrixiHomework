package com.trixi.hw.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorSchema {

    private Enum<ValidationCode> error;
    private String scope;
    private String message;
    private String detail;
    private HttpStatus httpStatus;

    public ErrorSchema() {}
}
