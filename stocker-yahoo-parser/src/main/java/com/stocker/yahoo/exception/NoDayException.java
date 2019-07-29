package com.stocker.yahoo.exception;

public class NoDayException extends Exception {
    public NoDayException(String message) {
        super(message);
    }

    public NoDayException() {
        super();
    }

    public NoDayException(String message, Throwable cause) {
        super(message, cause);
    }
}
