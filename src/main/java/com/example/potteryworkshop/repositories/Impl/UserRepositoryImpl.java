package com.example.potteryworkshop.repositories.Impl;

import com.example.potteryworkshop.models.entities.User;
import com.example.potteryworkshop.repositories.BaseRepository;
import com.example.potteryworkshop.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl extends BaseRepository<User, UUID> implements UserRepository {

    @PersistenceContext
    public EntityManager entityManager;

    protected UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }
}
