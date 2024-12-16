package com.example.potteryworkshop.exceptions.role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String name) {
        super("Роли '" + name + "' не существует!");
    }
}
