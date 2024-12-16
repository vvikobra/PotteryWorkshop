package com.example.potteryworkshop;

import com.example.potteryworkshop.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Clr implements CommandLineRunner {

    private final OrderService orderService;

    public Clr(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        updateOrders();
    }

    private void updateOrders() {
        orderService.updatePastOrders();
    }
}
