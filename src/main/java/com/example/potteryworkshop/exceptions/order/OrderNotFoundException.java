package com.example.potteryworkshop.exceptions.order;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID id) {
        super("Заказа с id: " + id + " не существует!");
    }
}
