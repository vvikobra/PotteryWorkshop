package com.example.potteryworkshop.services.Impl;

import com.example.potteryworkshop.exceptions.category.InvalidCategoryDataException;
import com.example.potteryworkshop.models.dtos.CategoryDTO;
import com.example.potteryworkshop.models.entities.Category;
import com.example.potteryworkshop.repositories.Impl.CategoryRepositoryImpl;
import com.example.potteryworkshop.services.CategoryService;
import com.example.potteryworkshop.util.validation.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepositoryImpl categoryRepository;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepositoryImpl categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        if (!this.validationUtil.isValid(categoryDTO)) {
            this.validationUtil
                    .violations(categoryDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidCategoryDataException();
        }
        Category category = new Category(categoryDTO.getName());
        categoryRepository.create(category);
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(category -> new CategoryDTO(category.getId(), category.getName())).toList();
    }

}
