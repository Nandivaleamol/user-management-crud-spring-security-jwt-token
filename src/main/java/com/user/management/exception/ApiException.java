package com.user.management.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super();
    }
}
