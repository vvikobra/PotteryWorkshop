package com.example.potteryworkshop.services.Impl;

import com.example.potteryworkshop.exceptions.role.InvalidRoleDataException;
import com.example.potteryworkshop.models.dtos.StatusDTO;
import com.example.potteryworkshop.models.entities.Status;
import com.example.potteryworkshop.repositories.Impl.StatusRepositoryImpl;
import com.example.potteryworkshop.services.StatusService;
import com.example.potteryworkshop.util.Mapper;
import com.example.potteryworkshop.util.validation.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    private final ValidationUtil validationUtil;
    private StatusRepositoryImpl statusRepository;

    public StatusServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setStatusRepository(StatusRepositoryImpl statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void addStatus(StatusDTO statusDTO) {
        if (!this.validationUtil.isValid(statusDTO)) {
            this.validationUtil
                    .violations(statusDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidRoleDataException();
        }
        Status status = new Status(statusDTO.getName());
        statusRepository.create(status);
    }

    @Override
    public List<StatusDTO> findAllStatuses() {
        return statusRepository.findAll().stream().map(status -> new StatusDTO(status.getId(), status.getName())).toList();
    }
}
