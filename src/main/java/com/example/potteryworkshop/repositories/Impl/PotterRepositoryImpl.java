package com.example.potteryworkshop.repositories.Impl;

import com.example.potteryworkshop.models.entities.Potter;
import com.example.potteryworkshop.repositories.BaseRepository;
import com.example.potteryworkshop.repositories.PotterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PotterRepositoryImpl extends BaseRepository<Potter, UUID> implements PotterRepository {
    protected PotterRepositoryImpl() {
        super(Potter.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Potter> findEmployedPotters() {
        return entityManager.createQuery("FROM Potter p WHERE p.employed = true", Potter.class)
                .getResultList();
    }
}