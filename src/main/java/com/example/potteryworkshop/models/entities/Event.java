package com.example.potteryworkshop.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {
    private String name;
    private int duration;
    private int cost;
    private int maxParticipants;
    private String description;
    private LocalDateTime date;
    private String imageUrl;
    private Category category;
    private Difficulty difficulty;
    private Potter potter;
    private Set<Order> orders;

    public Event(String name, int duration, int cost, int maxParticipants, String description, LocalDateTime date, String imageUrl, Category category, Difficulty difficulty, Potter potter) {
        this.name = name;
        this.duration = duration;
        this.cost = cost;
        this.maxParticipants = maxParticipants;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
        this.category = category;
        this.difficulty = difficulty;
        this.potter = potter;
    }

    protected Event() {

    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Column(nullable = false)
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Column(name = "max_participants", nullable = false)
    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Column
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "difficulty_id", referencedColumnName = "id")
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @ManyToOne
    @JoinColumn(name = "potter_id", referencedColumnName = "id")
    public Potter getPotter() {
        return potter;
    }

    public void setPotter(Potter potter) {
        this.potter = potter;
    }

    @OneToMany(mappedBy = "event", targetEntity = Order.class)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
