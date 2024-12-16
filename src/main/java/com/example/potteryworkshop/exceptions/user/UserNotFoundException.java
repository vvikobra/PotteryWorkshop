package com.example.potteryworkshop.exceptions.user;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("Пользователя с id: " + id + " не существует!");
    }
    public UserNotFoundException(String email) {
        super("Пользователя с email: " + email + " не существует!");
    }
}
