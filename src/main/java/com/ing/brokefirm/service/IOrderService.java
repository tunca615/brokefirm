package com.ing.brokefirm.service;

import com.ing.brokefirm.model.Order;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {
    Order createOrder(Order order, Principal principal);

    List<Order> listOrders(Long customerId, LocalDateTime startDate, LocalDateTime endDate, Principal principal);

    void cancelOrder(Long orderId, Principal principal);
}
