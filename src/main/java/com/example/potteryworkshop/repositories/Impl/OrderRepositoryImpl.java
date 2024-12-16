package com.example.potteryworkshop.repositories.Impl;

import com.example.potteryworkshop.models.entities.Order;
import com.example.potteryworkshop.repositories.BaseRepository;
import com.example.potteryworkshop.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepositoryImpl extends BaseRepository<Order, UUID> implements OrderRepository {
    @PersistenceContext
    public EntityManager entityManager;

    protected OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public Long countByEventId(UUID eventId) {
        return entityManager.createQuery("SELECT COALESCE(SUM(o.ticketQuantity), 0) FROM Order o WHERE o.event.id = :eventId AND " +
                        "(o.status.name = 'ACTUAL' OR o.status.name = 'COMPLETED')", Long.class)
                .setParameter("eventId", eventId)
                .getSingleResult();
    }

    @Override
    public List<Order> findByUserIdAndStatus(UUID userId, String statusName) {
        return entityManager.createQuery("FROM Order o WHERE o.user.id = :userId AND o.status.name = :statusName ORDER BY o.event.date DESC", Order.class)
                .setParameter("userId", userId)
                .setParameter("statusName", statusName)
                .getResultList();
    }
}