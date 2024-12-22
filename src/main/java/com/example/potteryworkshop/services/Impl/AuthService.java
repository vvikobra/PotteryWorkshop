package com.example.potteryworkshop.services.Impl;

import com.example.potteryworkshop.exceptions.user.PasswordsNotMatchException;
import com.example.potteryworkshop.exceptions.user.UserAlreadyExistsException;
import com.example.potteryworkshop.exceptions.user.UserNotFoundException;
import com.example.potteryworkshop.models.dtos.input.UserRegistrationDTO;
import com.example.potteryworkshop.models.entities.User;
import com.example.potteryworkshop.models.enums.UserRoles;
import com.example.potteryworkshop.repositories.Impl.RoleRepositoryImpl;
import com.example.potteryworkshop.repositories.Impl.UserRepositoryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepositoryImpl userRepository;

    private final RoleRepositoryImpl userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepositoryImpl userRepository, RoleRepositoryImpl userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public void register(UserRegistrationDTO registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new PasswordsNotMatchException();
        }

        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new UserAlreadyExistsException(registrationDTO.getEmail());
        }

        var userRole = userRoleRepository.
                findByName(UserRoles.USER).orElseThrow();

        User user = new User(
                passwordEncoder.encode(registrationDTO.getPassword()),
                registrationDTO.getUsername(),
                registrationDTO.getEmail()
        );

        user.setRoles(Set.of(userRole));

        this.userRepository.create(user);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
}
