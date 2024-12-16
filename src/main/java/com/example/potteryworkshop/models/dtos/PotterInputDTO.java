package com.example.potteryworkshop.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class PotterInputDTO {
    private String name;
    private LocalDate employmentDate;
    private String imageUrl;
    private Set<UUID> eventsId;

    public PotterInputDTO() {}

    public PotterInputDTO(String name, LocalDate employmentDate, String imageUrl) {
        this.name = name;
        this.employmentDate = employmentDate;
        this.imageUrl = imageUrl;
    }

    @NotNull
    @Length(min = 3, message = "Potter name must be more than two characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Past
    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<UUID> getEventsId() {
        return eventsId;
    }

    public void setEventsId(Set<UUID> eventsId) {
        this.eventsId = eventsId;
    }
}
