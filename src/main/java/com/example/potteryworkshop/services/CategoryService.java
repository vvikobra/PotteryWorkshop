package com.example.potteryworkshop.services;

import com.example.potteryworkshop.models.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> findAllCategories();
}
