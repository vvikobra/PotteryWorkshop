package com.example.potteryworkshop.repositories.Impl;


import com.example.potteryworkshop.models.entities.Category;
import com.example.potteryworkshop.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CategoryRepositoryImpl extends BaseRepository<Category, UUID> {

    protected CategoryRepositoryImpl() {
        super(Category.class);
    }

}
