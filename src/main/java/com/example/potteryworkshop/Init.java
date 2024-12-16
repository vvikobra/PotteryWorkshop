package com.example.potteryworkshop;

import com.example.potteryworkshop.models.entities.User;
import com.example.potteryworkshop.models.enums.UserRoles;
import com.example.potteryworkshop.repositories.Impl.RoleRepositoryImpl;
import com.example.potteryworkshop.repositories.Impl.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Init implements CommandLineRunner {
    private final UserRepositoryImpl userRepository;

    private final RoleRepositoryImpl userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    public Init(UserRepositoryImpl userRepository, RoleRepositoryImpl userRoleRepository, PasswordEncoder passwordEncoder, @Value("${app.default.password}") String defaultPassword) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void run(String... args) {
        initUsers();
    }

    private void initUsers() {
        if (userRepository.findAll().isEmpty()) {
            initAdmin();
            initNormalUser();
        }
    }

    private void initAdmin() {
        var adminRole = userRoleRepository.
                findByName(UserRoles.ADMIN).orElseThrow();
        System.out.println(adminRole.getName());
        var adminUser = new User(passwordEncoder.encode(defaultPassword), "Admin Adminovich", "admin@example.com");
        adminUser.setRoles(Set.of(adminRole));

        userRepository.create(adminUser);
    }

    private void initNormalUser() {
        var userRole = userRoleRepository.
                findByName(UserRoles.USER).orElseThrow();

        var normalUser = new User(passwordEncoder.encode(defaultPassword), "User Userovich", "user@example.com");
        normalUser.setRoles(Set.of(userRole));

        userRepository.create(normalUser);
    }
}
