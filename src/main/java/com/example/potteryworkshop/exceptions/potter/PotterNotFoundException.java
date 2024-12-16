package com.example.potteryworkshop.exceptions.potter;

import java.util.UUID;

public class PotterNotFoundException extends RuntimeException {
    public PotterNotFoundException(String name) {
        super("Гончара с именем: " + name + " не существует!");
    }
    public PotterNotFoundException(UUID id) {
        super("Гончара с id: " + id + " не существует!");
    }
}
