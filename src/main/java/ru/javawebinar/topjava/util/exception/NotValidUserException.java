package ru.javawebinar.topjava.util.exception;

public class NotValidUserException extends TopjavaException {
    public NotValidUserException() {
        this("Invalid User");
    }
    public NotValidUserException(String msg) {
        super(msg);
    }
}
