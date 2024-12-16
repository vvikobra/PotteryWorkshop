package com.example.potteryworkshop.services;


import com.example.potteryworkshop.models.dtos.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> findAllRoles();
}
