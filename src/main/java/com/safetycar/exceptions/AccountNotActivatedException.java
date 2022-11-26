package com.safetycar.exceptions;

public class AccountNotActivatedException extends RuntimeException {

    private static final String ACCOUNT_IS_NOT_ACTIVATED = "Account is not activated. Please check your email for an activation link.";

    public AccountNotActivatedException() {
        super(ACCOUNT_IS_NOT_ACTIVATED);
    }

    public AccountNotActivatedException(String message) {
        super(message);
    }
}
