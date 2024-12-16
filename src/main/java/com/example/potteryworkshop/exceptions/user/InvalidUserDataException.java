package com.example.potteryworkshop.exceptions.user;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException() {
        super("Некорректные данные для пользователя!");
    }
}
