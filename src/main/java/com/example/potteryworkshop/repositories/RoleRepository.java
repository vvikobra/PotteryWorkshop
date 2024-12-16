package com.example.potteryworkshop.repositories;

import com.example.potteryworkshop.models.entities.Role;
import com.example.potteryworkshop.models.enums.UserRoles;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(UserRoles name);
}
