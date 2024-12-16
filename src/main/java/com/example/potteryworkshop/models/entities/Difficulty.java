package com.example.potteryworkshop.models.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "difficulties")
public class Difficulty extends BaseEntity {

    private String name;
    private Set<Event> events;

    public Difficulty(String name) {
        this.name = name;
    }

    protected Difficulty() {}

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "difficulty", targetEntity = Event.class)
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
