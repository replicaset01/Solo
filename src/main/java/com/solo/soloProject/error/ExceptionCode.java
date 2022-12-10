package com.solo.soloProject.error;

import lombok.Getter;

public enum ExceptionCode {

    TODO_NOT_FOUND(404, "Todo Not Fount"),
    TODO_EXISTS(409, "Todo Exists");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
