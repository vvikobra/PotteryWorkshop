package com.example.potteryworkshop.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "potters")
public class Potter extends BaseEntity {

    private String name;
    private LocalDate employmentDate;
    private String imageUrl;
    private Set<Event> events;
    private boolean isEmployed;

    protected Potter() {
    }

    public Potter(String name, LocalDate employmentDate, String imageUrl) {
        this.name = name;
        this.employmentDate = employmentDate;
        this.imageUrl = imageUrl;
        this.isEmployed = true;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    @Column
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(name = "is_employed", nullable = false)
    public boolean getEmployed() {
        return isEmployed;
    }

    public void setEmployed(boolean isEmployed) {
        this.isEmployed = isEmployed;
    }

    @OneToMany(mappedBy = "potter", targetEntity = Event.class)
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
