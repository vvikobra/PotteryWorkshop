package com.example.potteryworkshop.exceptions.order;

public class InvalidOrderDataException extends RuntimeException {

    public InvalidOrderDataException() {
        super("Некорректные данные для заказа!");
    }
}
