package com.example.potteryworkshop.services.Impl;

import com.example.potteryworkshop.exceptions.category.CategoryNotFoundException;
import com.example.potteryworkshop.exceptions.difficulty.DifficultyNotFoundException;
import com.example.potteryworkshop.exceptions.event.EventNotFoundException;
import com.example.potteryworkshop.exceptions.event.InvalidEventDataException;
import com.example.potteryworkshop.exceptions.potter.PotterNotFoundException;
import com.example.potteryworkshop.models.dtos.EventInputDTO;
import com.example.potteryworkshop.models.dtos.EventOutputDTO;
import com.example.potteryworkshop.models.entities.Category;
import com.example.potteryworkshop.models.entities.Difficulty;
import com.example.potteryworkshop.models.entities.Event;
import com.example.potteryworkshop.models.entities.Potter;
import com.example.potteryworkshop.repositories.Impl.CategoryRepositoryImpl;
import com.example.potteryworkshop.repositories.Impl.DifficultyRepositoryImpl;
import com.example.potteryworkshop.repositories.Impl.EventRepositoryImpl;
import com.example.potteryworkshop.repositories.Impl.PotterRepositoryImpl;
import com.example.potteryworkshop.services.EventService;
import com.example.potteryworkshop.util.validation.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@EnableCaching
public class EventServiceImpl implements EventService {

    private EventRepositoryImpl eventRepository;
    private CategoryRepositoryImpl categoryRepository;
    private DifficultyRepositoryImpl difficultyRepository;
    private PotterRepositoryImpl potterRepository;
    private final ValidationUtil validationUtil;

    public EventServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setEventRepository(EventRepositoryImpl eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepositoryImpl categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setDifficultyRepository(DifficultyRepositoryImpl difficultyRepository) {
        this.difficultyRepository = difficultyRepository;
    }

    @Autowired
    public void setPotterRepository(PotterRepositoryImpl potterRepository) {
        this.potterRepository = potterRepository;
    }

    @Override
    @CacheEvict(cacheNames = "events", allEntries = true)
    public void addEvent(EventInputDTO eventDTO) {
        if (!this.validationUtil.isValid(eventDTO)) {
            this.validationUtil
                    .violations(eventDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidEventDataException();
        }
        if (eventDTO.getDate().isBefore(LocalDateTime.now())) throw new InvalidEventDataException();
        Category category = categoryRepository.findByName(eventDTO.getCategoryName()).orElseThrow(() -> new CategoryNotFoundException(eventDTO.getCategoryName()));
        Difficulty difficulty = difficultyRepository.findByName(eventDTO.getDifficultyName()).orElseThrow(() -> new DifficultyNotFoundException(eventDTO.getDifficultyName()));
        Potter potter = potterRepository.findByName(eventDTO.getPotterName()).orElseThrow(() -> new PotterNotFoundException(eventDTO.getPotterName()));
        Event event = new Event(eventDTO.getName(), eventDTO.getDuration(), eventDTO.getCost(), eventDTO.getMaxParticipants(), eventDTO.getDescription(), eventDTO.getDate(), eventDTO.getImageUrl(), category, difficulty, potter);
        eventRepository.create(event);
    }

    @Override
    public EventOutputDTO findById(UUID id) {
        return mapEventToEventDTO(eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id)));
    }

    @Override
    @Cacheable("events")
    public List<EventOutputDTO> findAllEvents() {
        return eventRepository.findActualEvents().stream().map(this::mapEventToEventDTO).toList();
    }

    @Override
    @CacheEvict(cacheNames = "events", allEntries = true)
    public void updateEvent(UUID id, EventInputDTO eventDTO) {
        if (!this.validationUtil.isValid(eventDTO)) {
            this.validationUtil
                    .violations(eventDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidEventDataException();
        }

        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        event.setName(eventDTO.getName());
        event.setCost(eventDTO.getCost());
        event.setDate(eventDTO.getDate());
        event.setDuration(eventDTO.getDuration());
        event.setMaxParticipants(eventDTO.getMaxParticipants());
        event.setDescription(eventDTO.getDescription());
        event.setImageUrl(eventDTO.getImageUrl());
        Category category = categoryRepository.findByName(eventDTO.getCategoryName()).orElseThrow(() -> new CategoryNotFoundException(eventDTO.getCategoryName()));
        Difficulty difficulty = difficultyRepository.findByName(eventDTO.getDifficultyName()).orElseThrow(() -> new DifficultyNotFoundException(eventDTO.getDifficultyName()));
        Potter potter = potterRepository.findByName(eventDTO.getPotterName()).orElseThrow(() -> new PotterNotFoundException(eventDTO.getPotterName()));
        event.setCategory(category);
        event.setDifficulty(difficulty);
        event.setPotter(potter);

        eventRepository.update(event);
    }

    @Override
    public List<EventOutputDTO> showUpcomingEvents() {
        return eventRepository.findUpcomingEvents().stream().map(this::mapEventToEventDTO).toList();
    }

    @Override
    public List<EventOutputDTO> showDiscountedEvents() {
        return eventRepository.findDiscountedEvents().stream()
                .map(this::mapEventToEventDTO)
                .toList();
    }

    @Override
    public List<EventOutputDTO> showTopEvents() {
        return eventRepository.findTopEvents().stream().map(this::mapEventToEventDTO).toList();
    }

    @Override
    public List<EventOutputDTO> showPottersEvents(UUID potterId) {
        return eventRepository.findByPotterId(potterId).stream().map(this::mapEventToEventDTO).toList();
    }

    private EventOutputDTO mapEventToEventDTO(Event event) {
        EventOutputDTO eventDTO = new EventOutputDTO();
        eventDTO.setId(event.getId());
        eventDTO.setName(event.getName());
        eventDTO.setCost(event.getCost());
        eventDTO.setDuration(event.getDuration());
        eventDTO.setMaxParticipants(event.getMaxParticipants());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setDate(event.getDate());
        eventDTO.setImageUrl(event.getImageUrl());
        eventDTO.setCategoryName(event.getCategory().getName());
        eventDTO.setDifficultyName(event.getDifficulty().getName());
        eventDTO.setPotterName(event.getPotter().getName());
        eventDTO.setPotterId(event.getPotter().getId());
        eventDTO.setDiscountCost((int) (event.getCost() * 0.8));
        return eventDTO;
    }
}
