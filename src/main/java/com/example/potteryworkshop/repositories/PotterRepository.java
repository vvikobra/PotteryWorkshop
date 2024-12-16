package com.example.potteryworkshop.repositories;

import com.example.potteryworkshop.models.entities.Potter;

import java.util.Optional;
import java.util.List;

public interface PotterRepository {
    List<Potter> findEmployedPotters();
    Optional<Potter> findByName(String name);
}
