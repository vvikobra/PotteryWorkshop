package com.example.potteryworkshop.repositories;

import com.example.potteryworkshop.models.entities.Difficulty;

import java.util.Optional;

public interface DifficultyRepository {
    Optional<Difficulty> findByName(String name);
}
