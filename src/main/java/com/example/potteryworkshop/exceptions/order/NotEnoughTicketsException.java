package com.example.potteryworkshop.exceptions.order;

public class NotEnoughTicketsException extends RuntimeException {
    public NotEnoughTicketsException() {
        super("Слишком большое количество выбранных билетов");
    }
}
