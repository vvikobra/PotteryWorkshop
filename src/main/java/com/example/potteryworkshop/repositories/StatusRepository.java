package com.example.potteryworkshop.repositories;

import com.example.potteryworkshop.models.entities.Status;

import java.util.Optional;

public interface StatusRepository {
    Optional<Status> findByName(String name);
}
