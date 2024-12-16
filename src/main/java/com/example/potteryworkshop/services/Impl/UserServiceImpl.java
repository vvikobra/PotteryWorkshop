package com.example.potteryworkshop.services.Impl;

import com.example.potteryworkshop.exceptions.user.InvalidUserDataException;
import com.example.potteryworkshop.exceptions.user.PasswordsNotMatchException;
import com.example.potteryworkshop.exceptions.user.UserAlreadyExistsException;
import com.example.potteryworkshop.exceptions.user.UserNotFoundException;
import com.example.potteryworkshop.models.dtos.UserInputDTO;
import com.example.potteryworkshop.models.dtos.UserOutputDTO;
import com.example.potteryworkshop.models.entities.User;
import com.example.potteryworkshop.repositories.Impl.UserRepositoryImpl;
import com.example.potteryworkshop.services.UserService;
import com.example.potteryworkshop.util.validation.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private UserRepositoryImpl userRepository;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(PasswordEncoder passwordEncoder, ValidationUtil validationUtil) {
        this.passwordEncoder = passwordEncoder;
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setUserRepository(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserOutputDTO> findAllUsers() {
        return userRepository.findAll().stream().map(this::mapUserToUserDTO).toList();
    }

    @Override
    public UserOutputDTO findById(UUID id) {
        return mapUserToUserDTO(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Override
    public void updateUser(UserInputDTO userDTO) {
        if (!this.validationUtil.isValid(userDTO)) {
            this.validationUtil
                    .violations(userDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidUserDataException();
        }
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() -> new UserNotFoundException(userDTO.getEmail()));
        user.setName(userDTO.getName());
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) throw new PasswordsNotMatchException();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.update(user);
    }

    @Override
    public UserOutputDTO findByEmail(String email) {
        return mapUserToUserDTO(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email)));
    }

    private UserOutputDTO mapUserToUserDTO(User user) {
        return new UserOutputDTO(user.getId(), user.getPassword(), user.getName(), user.getEmail());
    }

}