package com.safetycar.exceptions;

public class FileStorageException extends RuntimeException {

    public static final String FILE_HAS_NOT_BEEN_SAVED_S = "File has not been saved: %s";

    public FileStorageException(String message) {
        super(getFullMessage(message));
    }

    public FileStorageException(String message, Throwable cause) {
        super(getFullMessage(message), cause);
    }

    private static String getFullMessage(String message) {
        return String.format(FILE_HAS_NOT_BEEN_SAVED_S, message);
    }
}
