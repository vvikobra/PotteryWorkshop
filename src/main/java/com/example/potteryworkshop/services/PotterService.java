package com.example.potteryworkshop.services;

import com.example.potteryworkshop.models.dtos.input.PotterInputDTO;
import com.example.potteryworkshop.models.dtos.output.PotterOutputDTO;

import java.util.List;
import java.util.UUID;

public interface PotterService {
    void addPotter(PotterInputDTO potterInputDTO);

    List<PotterOutputDTO> findAllPotters();

    PotterOutputDTO findById(UUID id);

    void updatePotter(UUID potterId, PotterInputDTO potterInputDTO);

    void dismissPotter(UUID potterId);

    List<PotterOutputDTO> findEmployedPotters();
}
