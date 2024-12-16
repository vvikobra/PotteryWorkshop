package com.example.potteryworkshop.repositories.Impl;

import com.example.potteryworkshop.models.entities.Status;
import com.example.potteryworkshop.repositories.BaseRepository;
import com.example.potteryworkshop.repositories.StatusRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class StatusRepositoryImpl extends BaseRepository<Status, UUID> implements StatusRepository {
    @PersistenceContext
    public EntityManager entityManager;

    protected StatusRepositoryImpl() {
        super(Status.class);
    }

    @Override
    public Optional<Status> findByName(String name) {
        return Optional.ofNullable(entityManager.createQuery("FROM Status s WHERE s.name = :name", Status.class)
                .setParameter("name", name)
                .getSingleResult());
    }
}
