package com.example.potteryworkshop.models.dtos.output;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderOutputDTO implements Serializable {
    private UUID id;
    private int ticketQuantity;
    private int totalCost;
    private LocalDate orderDate;
    private LocalDateTime eventDate;
    private UUID userId;
    private String statusName;
    private String eventName;

    public OrderOutputDTO(UUID id, int ticketQuantity, int totalCost, UUID userId, String statusName, String eventName, LocalDateTime eventDate) {
        this.id = id;
        this.ticketQuantity = ticketQuantity;
        this.totalCost = totalCost;
        this.userId = userId;
        this.statusName = statusName;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public OrderOutputDTO() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
}
