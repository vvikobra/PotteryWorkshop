package com.example.potteryworkshop.repositories;

import com.example.potteryworkshop.models.entities.Potter;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface PotterRepository {
    LocalDate findEmploymentDate(UUID potterId);
    List<Potter> findEmployedPotters();
    Optional<Potter> findByName(String name);
}
