package com.example.potteryworkshop.repositories.Impl;


import com.example.potteryworkshop.models.entities.Category;
import com.example.potteryworkshop.repositories.BaseRepository;
import com.example.potteryworkshop.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoryRepositoryImpl extends BaseRepository<Category, UUID> implements CategoryRepository {

    @PersistenceContext
    public EntityManager entityManager;

    protected CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return Optional.ofNullable(entityManager.createQuery("FROM Category c WHERE c.name = :name", Category.class)
                .setParameter("name", name)
                .getSingleResult());
    }
}
