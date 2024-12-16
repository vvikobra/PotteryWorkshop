package com.example.potteryworkshop.exceptions.role;

public class InvalidRoleDataException extends RuntimeException {
    public InvalidRoleDataException() {
        super("Некорректные данные для роли!");
    }
}