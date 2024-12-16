package com.example.potteryworkshop.repositories.Impl;

import com.example.potteryworkshop.models.entities.Role;
import com.example.potteryworkshop.models.enums.UserRoles;
import com.example.potteryworkshop.repositories.BaseRepository;
import com.example.potteryworkshop.repositories.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleRepositoryImpl extends BaseRepository<Role, UUID> implements RoleRepository {
    @PersistenceContext
    public EntityManager entityManager;

    protected RoleRepositoryImpl() {
        super(Role.class);
    }

    @Override
    public Optional<Role> findByName(UserRoles name) {
        return Optional.ofNullable(entityManager.createQuery("FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult());
    }
}
