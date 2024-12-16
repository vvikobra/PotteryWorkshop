package com.example.potteryworkshop.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class OrderInputDTO {

    private LocalDate orderDate;
    private int ticketQuantity;
    private int totalCost;

    private String email;
    private UUID eventId;

    public OrderInputDTO(int ticketQuantity, String email, UUID eventId) {
        this.ticketQuantity = ticketQuantity;
        this.email = email;
        this.eventId = eventId;
    }

    public OrderInputDTO() {}

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @NotNull
    @Min(value = 1)
    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    @NotNull
    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }
}
