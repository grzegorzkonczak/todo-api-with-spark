package com.checkrise.todos.exc;


public class DaoException extends Exception {

    private Exception originalException;

    public DaoException(Exception originalException, String msg) {
        super(msg);
        this.originalException = originalException;
    }
}
