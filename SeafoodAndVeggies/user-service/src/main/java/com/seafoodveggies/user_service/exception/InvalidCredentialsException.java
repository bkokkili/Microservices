package com.seafoodveggies.user_service.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String invalidPassword) {
        super(invalidPassword);
    }
}
