package com.stocker.telegram.exception;

public class UnexpectedCommandException extends Exception{
    public UnexpectedCommandException(String message) {
        super(String.format("Unexpected command %s", message));
    }
}
