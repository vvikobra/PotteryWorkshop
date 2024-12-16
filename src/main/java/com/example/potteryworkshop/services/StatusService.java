package com.example.potteryworkshop.services;


import com.example.potteryworkshop.models.dtos.StatusDTO;

import java.util.List;

public interface StatusService {
    void addStatus(StatusDTO statusDTO);
    List<StatusDTO> findAllStatuses();
}
