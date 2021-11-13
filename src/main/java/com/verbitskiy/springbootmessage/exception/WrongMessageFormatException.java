package com.verbitskiy.springbootmessage.exception;

public class WrongMessageFormatException extends RuntimeException{
    public WrongMessageFormatException(String message) {
        super(message);
    }
}
