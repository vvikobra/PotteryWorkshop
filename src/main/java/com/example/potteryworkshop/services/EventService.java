package com.example.potteryworkshop.services;

import com.example.potteryworkshop.models.dtos.input.EventInputDTO;
import com.example.potteryworkshop.models.dtos.output.EventOutputDTO;

import java.util.List;
import java.util.UUID;

public interface EventService {
    void addEvent(EventInputDTO eventDTO);

    EventOutputDTO findById(UUID eventId);

    List<EventOutputDTO> findAllEvents();

    void updateEvent(UUID id, EventInputDTO eventDTO);

    List<EventOutputDTO> showUpcomingEvents();

    List<EventOutputDTO> showDiscountedEvents();

    List<EventOutputDTO> showTopEvents();

    List<EventOutputDTO> showPottersEvents(UUID potterId);
}