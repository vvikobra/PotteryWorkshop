package com.example.potteryworkshop.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private LocalDate orderDate;
    private int ticketQuantity;
    private int totalCost;

    private User user;
    private Status status;
    private Event event;

    protected Order() {}

    public Order(int ticketQuantity, int totalCost, User user, Status status, Event event) {
        this.orderDate = LocalDate.now();
        this.ticketQuantity = ticketQuantity;
        this.totalCost = totalCost;
        this.user = user;
        this.status = status;
        this.event = event;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Column(nullable = false)
    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name= "event_id", referencedColumnName = "id")
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Column(nullable = false)
    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
