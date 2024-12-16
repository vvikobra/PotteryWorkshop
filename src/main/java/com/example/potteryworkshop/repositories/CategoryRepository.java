package com.example.potteryworkshop.repositories;


import com.example.potteryworkshop.models.entities.Category;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findByName(String name);
}
