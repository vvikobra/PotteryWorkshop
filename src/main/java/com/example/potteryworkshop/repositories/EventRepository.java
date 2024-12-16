package com.example.potteryworkshop.repositories;

import com.example.potteryworkshop.models.entities.Event;

import java.util.List;
import java.util.UUID;

public interface EventRepository {

    List<Event> findTopEvents();

    List<Event> findActualEvents();

    List<Event> findDiscountedEvents();

    List<Event> findUpcomingEvents();

    List<Event> findByPotterId(UUID potterId);

    List<Event> findByDifficulty(String difficulty);

}
