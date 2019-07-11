package com.stocker.telegram.exception;

public class UnexpectedCommandException extends Exception{
    public UnexpectedCommandException(String message) {
        super(message);
    }
}
