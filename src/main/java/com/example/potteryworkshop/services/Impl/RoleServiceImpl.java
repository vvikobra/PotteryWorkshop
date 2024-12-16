package com.example.potteryworkshop.services.Impl;

import com.example.potteryworkshop.models.dtos.RoleDTO;
import com.example.potteryworkshop.repositories.Impl.RoleRepositoryImpl;
import com.example.potteryworkshop.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepositoryImpl roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepositoryImpl roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        return roleRepository.findAll().stream().map(role -> new RoleDTO(role.getId(), role.getName().toString())).toList();
    }
}
