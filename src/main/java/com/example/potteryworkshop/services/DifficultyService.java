package com.example.potteryworkshop.services;

import com.example.potteryworkshop.models.dtos.DifficultyDTO;

import java.util.List;

public interface DifficultyService {
    void addDifficulty(DifficultyDTO difficultyDTO);

    List<DifficultyDTO> findAllDifficulties();
}
