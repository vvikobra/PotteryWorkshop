package com.example.potteryworkshop.services.Impl;

import com.example.potteryworkshop.exceptions.difficulty.InvalidDifficultyDataException;
import com.example.potteryworkshop.models.dtos.DifficultyDTO;
import com.example.potteryworkshop.models.entities.Difficulty;
import com.example.potteryworkshop.repositories.Impl.DifficultyRepositoryImpl;
import com.example.potteryworkshop.services.DifficultyService;
import com.example.potteryworkshop.util.validation.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DifficultyServiceImpl implements DifficultyService {

    private DifficultyRepositoryImpl difficultyRepository;
    private final ValidationUtil validationUtil;

    public DifficultyServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setDifficultyRepository(DifficultyRepositoryImpl difficultyRepository) {
        this.difficultyRepository = difficultyRepository;
    }

    @Override
    public void addDifficulty(DifficultyDTO difficultyDTO) {
        if (!this.validationUtil.isValid(difficultyDTO)) {
            this.validationUtil
                    .violations(difficultyDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidDifficultyDataException();
        }
        Difficulty difficulty = new Difficulty(difficultyDTO.getName());
        difficultyRepository.create(difficulty);
    }

    @Override
    public List<DifficultyDTO> findAllDifficulties() {
        return difficultyRepository.findAll().stream().map(difficulty -> new DifficultyDTO(difficulty.getId(), difficulty.getName())).toList();
    }
}
