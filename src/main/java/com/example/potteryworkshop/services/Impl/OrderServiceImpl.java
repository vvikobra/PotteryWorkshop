package com.example.potteryworkshop.services.Impl;

import com.example.potteryworkshop.exceptions.event.EventNotFoundException;
import com.example.potteryworkshop.exceptions.order.InvalidOrderDataException;
import com.example.potteryworkshop.exceptions.order.NotEnoughTicketsException;
import com.example.potteryworkshop.exceptions.order.OrderNotFoundException;
import com.example.potteryworkshop.exceptions.user.UserNotFoundException;
import com.example.potteryworkshop.models.dtos.OrderInputDTO;
import com.example.potteryworkshop.models.dtos.OrderOutputDTO;
import com.example.potteryworkshop.models.entities.Event;
import com.example.potteryworkshop.models.entities.Order;
import com.example.potteryworkshop.models.entities.Status;
import com.example.potteryworkshop.repositories.Impl.EventRepositoryImpl;
import com.example.potteryworkshop.repositories.Impl.OrderRepositoryImpl;
import com.example.potteryworkshop.repositories.Impl.StatusRepositoryImpl;
import com.example.potteryworkshop.repositories.Impl.UserRepositoryImpl;
import com.example.potteryworkshop.services.OrderService;
import com.example.potteryworkshop.util.validation.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@EnableCaching
public class OrderServiceImpl implements OrderService {

    private final ValidationUtil validationUtil;

    private OrderRepositoryImpl orderRepository;
    private StatusRepositoryImpl statusRepository;
    private EventRepositoryImpl eventRepository;
    private UserRepositoryImpl userRepository;

    public OrderServiceImpl(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setOrderRepository(OrderRepositoryImpl orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setStatusRepository(StatusRepositoryImpl statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Autowired
    public void setEventRepository(EventRepositoryImpl eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setUserRepository(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    private void validOrder(OrderInputDTO orderInputDTO) {
        if (!this.validationUtil.isValid(orderInputDTO)) {
            this.validationUtil
                    .violations(orderInputDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new InvalidOrderDataException();
        }
        if (userRepository.findByEmail(orderInputDTO.getEmail()).isEmpty())
            throw new UserNotFoundException(orderInputDTO.getEmail());
        if (!eventRepository.existsById(orderInputDTO.getEventId()))
            throw new EventNotFoundException(orderInputDTO.getEventId());
        Event event = eventRepository.findById(orderInputDTO.getEventId()).orElseThrow(() -> new EventNotFoundException(orderInputDTO.getEventId()));
        if (event.getMaxParticipants() - orderRepository.countByEventId(orderInputDTO.getEventId()) - orderInputDTO.getTicketQuantity() < 0) {
            throw new NotEnoughTicketsException();
        }
    }

    private Order mapOrderDTOToOrder(OrderInputDTO orderInputDTO, Boolean isDiscounted) {
        Event event = eventRepository.findById(orderInputDTO.getEventId()).orElseThrow(() -> new EventNotFoundException(orderInputDTO.getEventId()));
        if (isDiscounted) {
            return new Order(orderInputDTO.getTicketQuantity(),
                    (int) (orderInputDTO.getTicketQuantity() * event.getCost() * 0.8),
                    userRepository.findByEmail(orderInputDTO.getEmail())
                            .orElseThrow(() -> new UserNotFoundException(orderInputDTO.getEmail())),
                    statusRepository.findByName("ACTUAL").orElseThrow(),
                    event);
        } else return new Order(orderInputDTO.getTicketQuantity(),
                orderInputDTO.getTicketQuantity() * event.getCost(),
                userRepository.findByEmail(orderInputDTO.getEmail())
                        .orElseThrow(() -> new UserNotFoundException(orderInputDTO.getEmail())),
                statusRepository.findByName("ACTUAL").orElseThrow(),
                event);
    }

    @Override
    public void addOrder(OrderInputDTO orderInputDTO, Boolean isDiscounted) {
        validOrder(orderInputDTO);
        Order order = mapOrderDTOToOrder(orderInputDTO, isDiscounted);
        orderRepository.create(order);
    }

    @Override
    @CacheEvict(cacheNames = "canceledOrders", allEntries = true)
    public void cancelOrder(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        Status canceledStatus = statusRepository.findByName("CANCELED")
                .orElseThrow();
        order.setStatus(canceledStatus);
        orderRepository.update(order);
    }

    @Override
    @CacheEvict(cacheNames = "pastOrders", allEntries = true)
    public void updatePastOrders() {
        orderRepository.findAll().forEach(order -> {
            if (order.getEvent().getDate().isBefore(LocalDate.now().atStartOfDay()) && !order.getStatus().getName().equals("CANCELED")) {
                order.setStatus(statusRepository.findByName("COMPLETED").orElseThrow());
                orderRepository.update(order);
            }
        });
    }

    @Override
    public List<OrderOutputDTO> getActualOrders(UUID id) {
        return orderRepository.findByUserIdAndStatus(id, "ACTUAL").stream()
                .map(this::mapOrderToOrderDTO)
                .toList();
    }

    @Override
    @Cacheable("canceledOrders")
    public List<OrderOutputDTO> getCanceledOrders(UUID id) {
        return orderRepository.findByUserIdAndStatus(id, "CANCELED").stream()
                .map(this::mapOrderToOrderDTO)
                .toList();
    }

    @Override
    @Cacheable("pastOrders")
    public List<OrderOutputDTO> getPastOrders(UUID id) {
        return orderRepository.findByUserIdAndStatus(id, "COMPLETED").stream()
                .map(this::mapOrderToOrderDTO)
                .toList();
    }

    private OrderOutputDTO mapOrderToOrderDTO(Order order) {
        OrderOutputDTO orderOutputDTO = new OrderOutputDTO();
        orderOutputDTO.setId(order.getId());
        orderOutputDTO.setTicketQuantity(order.getTicketQuantity());
        orderOutputDTO.setTotalCost(order.getTotalCost());
        orderOutputDTO.setOrderDate(order.getOrderDate());
        orderOutputDTO.setStatusName(order.getStatus().getName());
        orderOutputDTO.setUserId(order.getUser().getId());
        orderOutputDTO.setEventName(order.getEvent().getName());
        orderOutputDTO.setEventDate(order.getEvent().getDate());
        return orderOutputDTO;
    }
}