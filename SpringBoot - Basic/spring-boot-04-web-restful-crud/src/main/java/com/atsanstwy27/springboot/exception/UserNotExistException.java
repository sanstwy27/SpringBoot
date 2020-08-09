package com.atkjs927.springboot.exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException() {
        super("User not Exist");
    }
}
