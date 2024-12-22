package com.example.potteryworkshop.repositories;

import com.example.potteryworkshop.models.entities.Potter;

import java.util.List;

public interface PotterRepository {
    List<Potter> findEmployedPotters();
}