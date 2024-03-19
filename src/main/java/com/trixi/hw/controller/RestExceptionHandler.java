package com.trixi.hw.controller;

import com.trixi.hw.error.CommonError;
import com.trixi.hw.error.ErrorSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CommonError.class)
    public final ResponseEntity<ErrorSchema> handleAllExceptions(CommonError error) {
        ErrorSchema errorSchema = new ErrorSchema();
        errorSchema.setError(error.getError());
        errorSchema.setScope(error.getScope());
        errorSchema.setMessage(error.getMessage());
        return new ResponseEntity<>(errorSchema, error.getHttpStatus());
    }
}
