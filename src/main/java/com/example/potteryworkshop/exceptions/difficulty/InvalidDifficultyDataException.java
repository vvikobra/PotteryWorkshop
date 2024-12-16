package com.example.potteryworkshop.exceptions.difficulty;

public class InvalidDifficultyDataException extends RuntimeException {
    public InvalidDifficultyDataException() {
        super("Некорректные данные для сложности!");
    }
}
