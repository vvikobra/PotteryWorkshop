package com.example.potteryworkshop.services.Impl;

import com.example.potteryworkshop.exceptions.potter.InvalidPotterDataException;
import com.example.potteryworkshop.exceptions.potter.PotterNotFoundException;
import com.example.potteryworkshop.models.dtos.input.PotterInputDTO;
import com.example.potteryworkshop.models.dtos.output.PotterOutputDTO;
import com.example.potteryworkshop.models.entities.Potter;
import com.example.potteryworkshop.repositories.Impl.PotterRepositoryImpl;
import com.example.potteryworkshop.services.PotterService;
import com.example.potteryworkshop.util.validation.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
@EnableCaching
public class PotterServiceImpl implements PotterService {

    private PotterRepositoryImpl potterRepository;
    private final ValidationUtil validationUtil;

    public PotterServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setPotterRepository(PotterRepositoryImpl potterRepository) {
        this.potterRepository = potterRepository;
    }

    @Override
    @CacheEvict(cacheNames = {"adminPotters", "potters"}, allEntries = true)
    public void addPotter(PotterInputDTO potterInputDTO) {
        if (!this.validationUtil.isValid(potterInputDTO)) {
            this.validationUtil
                    .violations(potterInputDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidPotterDataException();
        }
        Potter potter = new Potter(potterInputDTO.getName(), potterInputDTO.getEmploymentDate(), potterInputDTO.getImageUrl());
        potterRepository.create(potter);
    }

    @Override
    @Cacheable("adminPotters")
    public List<PotterOutputDTO> findAllPotters() {
        return potterRepository.findAll().stream().map(this::mapPotterToPotterDTO).toList();
    }

    @Override
    public PotterOutputDTO findById(UUID id) {
        return mapPotterToPotterDTO(potterRepository.findById(id).orElseThrow(() -> new PotterNotFoundException(id)));
    }

    @Override
    @CacheEvict(cacheNames = {"adminPotters", "potters"}, allEntries = true)
    public void updatePotter(UUID potterId, PotterInputDTO potterInputDTO) {
        if (!this.validationUtil.isValid(potterInputDTO)) {
            this.validationUtil
                    .violations(potterInputDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidPotterDataException();
        }

        Potter potter = potterRepository.findById(potterId).orElseThrow(() -> new PotterNotFoundException(potterId));
        potter.setName(potterInputDTO.getName());
        potter.setEmploymentDate(potterInputDTO.getEmploymentDate());
        potter.setImageUrl(potterInputDTO.getImageUrl());
        potterRepository.update(potter);
    }

    @Override
    @CacheEvict(cacheNames = {"adminPotters", "potters"}, allEntries = true)
    public void dismissPotter(UUID potterId) {
        Potter potter = potterRepository.findById(potterId).orElseThrow(() -> new PotterNotFoundException(potterId));
        potter.setEmployed(false);
        potterRepository.update(potter);
    }

    @Override
    @Cacheable("potters")
    public List<PotterOutputDTO> findEmployedPotters() {
        return potterRepository.findEmployedPotters().stream().map(this::mapPotterToPotterDTO).toList();
    }

    private PotterOutputDTO mapPotterToPotterDTO(Potter potter) {
        PotterOutputDTO potterOutputDTO = new PotterOutputDTO();
        potterOutputDTO.setId(potter.getId());
        potterOutputDTO.setName(potter.getName());
        potterOutputDTO.setImageUrl(potter.getImageUrl());
        potterOutputDTO.setEmployed(potter.getEmployed());
        Period period = Period.between(potter.getEmploymentDate(), LocalDate.now());
        potterOutputDTO.setEmploymentDate(potter.getEmploymentDate());
        potterOutputDTO.setExperienceYears(period.getYears());
        potterOutputDTO.setExperienceMonths(period.getMonths());
        return potterOutputDTO;
    }
}