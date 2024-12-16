package com.example.potteryworkshop.services;


import com.example.potteryworkshop.models.dtos.UserInputDTO;
import com.example.potteryworkshop.models.dtos.UserOutputDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserOutputDTO> findAllUsers();
    UserOutputDTO findById(UUID id);
    void updateUser(UserInputDTO userDTO);
    UserOutputDTO findByEmail(String email);
}
