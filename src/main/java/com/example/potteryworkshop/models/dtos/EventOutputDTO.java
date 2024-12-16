package com.example.potteryworkshop.models.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class EventOutputDTO implements Serializable {
    private UUID id;
    private String name;
    private int duration;
    private int cost;
    private int discountCost;
    private int maxParticipants;
    private String description;
    private LocalDateTime date;
    private String imageUrl;
    private String categoryName;
    private String difficultyName;
    private UUID potterId;
    private String potterName;
    private Set<UUID> ordersId;

    public EventOutputDTO(UUID id, String name, int duration, int cost, int maxParticipants, String description, LocalDateTime date, String imageUrl, String categoryName, String difficultyName, UUID potterId, String potterName) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.cost = cost;
        this.maxParticipants = maxParticipants;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
        this.difficultyName = difficultyName;
        this.potterId = potterId;
        this.potterName = potterName;
    }

    public EventOutputDTO() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDifficultyName() {
        return difficultyName;
    }

    public void setDifficultyName(String difficultyName) {
        this.difficultyName = difficultyName;
    }

    public UUID getPotterId() {
        return potterId;
    }

    public void setPotterId(UUID potterId) {
        this.potterId = potterId;
    }

    public Set<UUID> getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Set<UUID> ordersId) {
        this.ordersId = ordersId;
    }

    public String getPotterName() {
        return potterName;
    }

    public void setPotterName(String potterName) {
        this.potterName = potterName;
    }

    public int getDiscountCost() {
        return discountCost;
    }

    public void setDiscountCost(int discountCost) {
        this.discountCost = discountCost;
    }
}
