package com.example.potteryworkshop.models.dtos.output;

import java.util.Set;
import java.util.UUID;

public class UserOutputDTO {
    private UUID id;
    private String password;
    private String name;
    private String email;
    private Set<UUID> ordersId;

    public UserOutputDTO(UUID id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public UserOutputDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UUID> getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Set<UUID> ordersId) {
        this.ordersId = ordersId;
    }
}
