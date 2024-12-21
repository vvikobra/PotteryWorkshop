package com.example.potteryworkshop.repositories.Impl;

import com.example.potteryworkshop.models.entities.Difficulty;
import com.example.potteryworkshop.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DifficultyRepositoryImpl extends BaseRepository<Difficulty, UUID> {

    protected DifficultyRepositoryImpl() {
        super(Difficulty.class);
    }

}
