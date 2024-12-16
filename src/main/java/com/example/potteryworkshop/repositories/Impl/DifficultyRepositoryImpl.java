package com.example.potteryworkshop.repositories.Impl;


import com.example.potteryworkshop.models.entities.Difficulty;
import com.example.potteryworkshop.repositories.BaseRepository;
import com.example.potteryworkshop.repositories.DifficultyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class DifficultyRepositoryImpl extends BaseRepository<Difficulty, UUID> implements DifficultyRepository {
    @PersistenceContext
    public EntityManager entityManager;

    protected DifficultyRepositoryImpl() {
        super(Difficulty.class);
    }

    @Override
    public Optional<Difficulty> findByName(String name) {
        return Optional.ofNullable(entityManager.createQuery("FROM Difficulty d WHERE d.name = :name", Difficulty.class)
                .setParameter("name", name)
                .getSingleResult());
    }
}
