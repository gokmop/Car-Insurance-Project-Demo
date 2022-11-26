package com.safetycar.exceptions;

public class TooManyOffersException extends RuntimeException {

    public TooManyOffersException() {
        super("Too many offers to consider!");
    }

    public TooManyOffersException(String message) {
        super(message);
    }

    public TooManyOffersException(String message, Throwable cause) {
        super(message, cause);
    }
}
