package com.example.potteryworkshop.services;

import com.example.potteryworkshop.models.dtos.UserInputDTO;
import com.example.potteryworkshop.models.dtos.UserOutputDTO;


public interface UserService {

    void updateUser(UserInputDTO userDTO);

    UserOutputDTO findByEmail(String email);
}
