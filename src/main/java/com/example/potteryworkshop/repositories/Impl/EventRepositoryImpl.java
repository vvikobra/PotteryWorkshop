package com.example.potteryworkshop.repositories.Impl;

import com.example.potteryworkshop.models.entities.Event;
import com.example.potteryworkshop.repositories.BaseRepository;
import com.example.potteryworkshop.repositories.EventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class EventRepositoryImpl extends BaseRepository<Event, UUID> implements EventRepository {

    @PersistenceContext
    public EntityManager entityManager;

    protected EventRepositoryImpl() {
        super(Event.class);
    }

    @Override
    public List<Event> findTopEvents() {
        return entityManager.createQuery(
                        "FROM Event e " +
                                "JOIN e.orders o " +
                                "JOIN o.status s " +
                                "WHERE s.name NOT LIKE 'CANCELED'" +
                                "AND e.date >= CURRENT_DATE " +
                                "GROUP BY e " +
                                "HAVING SUM(o.ticketQuantity) > 0 " +
                                "ORDER BY SUM(o.ticketQuantity) DESC " +
                                "LIMIT 5",
                        Event.class)
                .getResultList();
    }

    @Override
    public List<Event> findActualEvents() {
        LocalDateTime currentDate = LocalDateTime.now();
        return entityManager.createQuery(
                        "FROM Event e WHERE e.date >= :currentDate", Event.class)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    @Override
    public List<Event> findDiscountedEvents() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime threeDaysLater = currentDate.plusDays(3);

        return entityManager.createQuery(
                        "FROM Event e " +
                                "LEFT JOIN e.orders o " +
                                "LEFT JOIN o.status s " +
                                "WHERE e.date BETWEEN :currentDate AND :threeDaysLater " +
                                "GROUP BY e " +
                                "HAVING SUM(CASE WHEN s.name = 'ACTUAL' THEN o.ticketQuantity ELSE 0 END) < e.maxParticipants / 2 " +
                                "ORDER BY e.date",
                        Event.class)
                .setParameter("currentDate", currentDate)
                .setParameter("threeDaysLater", threeDaysLater)
                .getResultList();
    }

    @Override
    public List<Event> findUpcomingEvents() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime threeDaysLater = currentDate.plusDays(3);
        LocalDateTime tenDaysLater = currentDate.plusDays(10);
        return entityManager.createQuery("FROM Event e " +
                        "WHERE (e.date BETWEEN :startDate AND :endDate) " +
                        "AND e NOT IN (" +
                        "   SELECT e1 FROM Event e1 " +
                        "   LEFT JOIN e1.orders o" +
                        "   LEFT JOIN o.status s " +
                        "   WHERE e1.date BETWEEN :startDate AND :threeDaysLater " +
                        "   GROUP BY e1 " +
                        "   HAVING SUM(CASE WHEN s.name = 'ACTUAL' THEN o.ticketQuantity ELSE 0 END) < e.maxParticipants / 2" +
                        ") " +
                        "ORDER BY e.date", Event.class)
                .setParameter("startDate", currentDate)
                .setParameter("endDate", tenDaysLater)
                .setParameter("threeDaysLater", threeDaysLater)
                .getResultList();
    }

    @Override
    public List<Event> findByPotterId(UUID potterId) {
        LocalDateTime currentDate = LocalDateTime.now();
        return entityManager.createQuery("FROM Event e " +
                        "WHERE e.potter.id = :potterId " +
                        "AND e.date >= :currentDate", Event.class)
                .setParameter("potterId", potterId)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Event> findByDifficulty(String difficulty) {
        return entityManager.createQuery("FROM Event e WHERE e.difficulty = :difficulty", Event.class)
                .setParameter("difficulty", difficulty)
                .getResultList();
    }
}
