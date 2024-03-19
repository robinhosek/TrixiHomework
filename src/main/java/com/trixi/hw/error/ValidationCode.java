package com.trixi.hw.error;

public enum ValidationCode {

    ID_NOT_FOUND("ID_NOT_FOUND"),
    EMPTY_DB("EMPTY_DB")
    ;

    private final String value;

    ValidationCode(String value) {
        this.value = value;
    }
}
