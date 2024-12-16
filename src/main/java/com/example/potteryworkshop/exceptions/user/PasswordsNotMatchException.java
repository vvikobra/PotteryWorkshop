package com.example.potteryworkshop.exceptions.user;

public class PasswordsNotMatchException extends RuntimeException {
    public PasswordsNotMatchException() {
        super("Пароли не совпадают!");
    }
}
