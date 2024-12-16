package com.example.potteryworkshop.exceptions.potter;

public class InvalidPotterDataException extends RuntimeException {
    public InvalidPotterDataException() {
        super("Некорректные данные для гончара!");
    }
}