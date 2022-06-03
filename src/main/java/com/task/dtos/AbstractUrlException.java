package com.task.dtos;

public abstract class AbstractUrlException extends Exception {

    public AbstractUrlException() {
    }

    public AbstractUrlException(String message) {
        super(message);
    }
}
