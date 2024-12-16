package com.example.potteryworkshop.exceptions.event;

public class InvalidEventDataException extends RuntimeException {
    public InvalidEventDataException() {
        super("Некорректные данные для мероприятия!");
    }
}
