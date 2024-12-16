package com.example.potteryworkshop.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
import java.util.UUID;

public class CategoryDTO {

    private UUID id;
    private String name;
    private Set<UUID> eventsId;

    public CategoryDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @NotNull
    @NotEmpty
    @Length(min = 3, message = "Category name must be more than two characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UUID> getEventsId() {
        return eventsId;
    }

    public void setEventsId(Set<UUID> eventsId) {
        this.eventsId = eventsId;
    }
}
