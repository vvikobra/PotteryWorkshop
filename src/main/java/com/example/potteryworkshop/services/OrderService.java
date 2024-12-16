package com.example.potteryworkshop.services;

import com.example.potteryworkshop.models.dtos.OrderInputDTO;
import com.example.potteryworkshop.models.dtos.OrderOutputDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    void addOrder(OrderInputDTO orderInputDTO, Boolean isDiscounted);

    void cancelOrder(UUID id);

    void updatePastOrders();

    List<OrderOutputDTO> getActualOrders(UUID id);

    List<OrderOutputDTO> getCanceledOrders(UUID id);

    List<OrderOutputDTO> getPastOrders(UUID id);

}
