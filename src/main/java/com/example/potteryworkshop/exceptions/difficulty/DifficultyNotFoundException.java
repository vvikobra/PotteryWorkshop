package com.example.potteryworkshop.exceptions.difficulty;

public class DifficultyNotFoundException extends RuntimeException {
    public DifficultyNotFoundException(String name) {
        super("Сложности: '" + name + "' не существует!");
    }
}
