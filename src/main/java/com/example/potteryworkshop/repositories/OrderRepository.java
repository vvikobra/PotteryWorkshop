package com.example.potteryworkshop.repositories;

import com.example.potteryworkshop.models.entities.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    Long countByEventId(UUID eventId);
    List<Order> findByUserIdAndStatus(UUID userId, String statusName);
}
