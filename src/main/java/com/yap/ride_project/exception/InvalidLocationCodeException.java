package com.yap.ride_project.exception;

public class InvalidLocationCodeException extends RuntimeException{
    public InvalidLocationCodeException(String message) {
        super(message);
    }
}
