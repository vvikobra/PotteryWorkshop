package com.example.potteryworkshop.exceptions.category;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String name) {
        super("Категории: '" + name + "' не существует!");
    }
}
