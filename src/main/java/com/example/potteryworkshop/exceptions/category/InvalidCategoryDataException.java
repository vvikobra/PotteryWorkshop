package com.example.potteryworkshop.exceptions.category;

public class InvalidCategoryDataException extends RuntimeException {
    public InvalidCategoryDataException() {
        super("Некорректные данные для категории!");
    }
}
