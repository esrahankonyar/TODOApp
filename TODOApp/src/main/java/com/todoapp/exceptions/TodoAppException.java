package com.todoapp.exceptions;

public class TodoAppException extends RuntimeException {

    public TodoAppException(String message) {
        super(message);
    }

    public TodoAppException(String message, Throwable cause) {
        super(message, cause);
    }

}
