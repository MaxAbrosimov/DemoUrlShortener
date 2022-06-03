package com.task.dtos.exception;

public abstract class AbstractUrlException extends Exception {

    public AbstractUrlException() {
    }

    public AbstractUrlException(String message) {
        super(message);
    }
}
