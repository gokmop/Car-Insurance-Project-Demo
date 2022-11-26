package com.safetycar.exceptions;

public class ExpiredException extends RuntimeException {

    public ExpiredException() {
    }

    public ExpiredException(String message) {
        super(message);
    }

    public ExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
