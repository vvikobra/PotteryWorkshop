package com.example.potteryworkshop.repositories;

import com.example.potteryworkshop.models.entities.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
