package com.example.potteryworkshop.exceptions.event;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(UUID id) {
        super("Мероприятие с id: " + id + " не существует!");
    }
}
