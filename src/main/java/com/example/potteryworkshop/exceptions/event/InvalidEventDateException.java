package com.example.potteryworkshop.exceptions.event;

public class InvalidEventDateException extends RuntimeException{
    public InvalidEventDateException() {
        super("Дата мероприятия не может быть назначена раньше данного времени");
    }
}