package com.example.potteryworkshop.models.dtos.output;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class PotterOutputDTO implements Serializable {
    private UUID id;
    private String name;
    private int experienceYears;
    private int experienceMonths;
    private LocalDate employmentDate;
    private String imageUrl;
    private boolean isEmployed;
    private Set<UUID> eventsId;

    public PotterOutputDTO() {
    }

    public PotterOutputDTO(UUID id, String name, int experienceYears, int experienceMonths, LocalDate employmentDate, String imageUrl, boolean isEmployed, Set<UUID> eventsId) {
        this.id = id;
        this.name = name;
        this.experienceYears = experienceYears;
        this.experienceMonths = experienceMonths;
        this.employmentDate = employmentDate;
        this.imageUrl = imageUrl;
        this.isEmployed = isEmployed;
        this.eventsId = eventsId;
    }

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

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

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

    public int getExperienceMonths() {
        return experienceMonths;
    }

    public void setExperienceMonths(int experienceMonths) {
        this.experienceMonths = experienceMonths;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public void setEmployed(boolean employed) {
        isEmployed = employed;
    }
}
