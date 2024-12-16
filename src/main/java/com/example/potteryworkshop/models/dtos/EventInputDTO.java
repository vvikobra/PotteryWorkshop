package com.example.potteryworkshop.models.dtos;

import com.example.potteryworkshop.util.validation.FutureDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventInputDTO {
    private String name;
    private int duration;
    private int cost;
    private int maxParticipants;
    private String description;
    private LocalDateTime date;
    private String imageUrl;
    private String categoryName;
    private String difficultyName;
    private String potterName;

    public EventInputDTO(String name, int duration, int cost, int maxParticipants, String description, LocalDateTime date, String imageUrl, String categoryName, String difficultyName, String potterName) {
        this.name = name;
        this.duration = duration;
        this.cost = cost;
        this.maxParticipants = maxParticipants;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
        this.difficultyName = difficultyName;
        this.potterName = potterName;
    }

    public EventInputDTO() {

    }

    @NotNull
    @Length(min = 3, message = "Event name must be more than two characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Min(value = 30)
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @NotNull
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @NotNull
    @Min(value = 1)
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

    @NotNull
    @FutureDate
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

    @NotNull
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @NotNull
    public String getDifficultyName() {
        return difficultyName;
    }

    public void setDifficultyName(String difficultyName) {
        this.difficultyName = difficultyName;
    }

    @NotNull
    public String getPotterName() {
        return potterName;
    }

    public void setPotterName(String potterName) {
        this.potterName = potterName;
    }
}
