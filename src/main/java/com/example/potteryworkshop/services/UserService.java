package com.example.potteryworkshop.services;

import com.example.potteryworkshop.models.dtos.input.UserInputDTO;
import com.example.potteryworkshop.models.dtos.output.UserOutputDTO;


public interface UserService {

    void updateUser(UserInputDTO userDTO);

    UserOutputDTO findByEmail(String email);
}
