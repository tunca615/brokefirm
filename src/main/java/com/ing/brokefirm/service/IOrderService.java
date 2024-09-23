package com.ing.brokefirm.service;

import com.ing.brokefirm.model.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {
    Order createOrder(Order order, UsernamePasswordAuthenticationToken principal);

    List<Order> listOrders(Long customerId, LocalDateTime startDate, LocalDateTime endDate, UsernamePasswordAuthenticationToken principal);

    void cancelOrder(Long orderId, UsernamePasswordAuthenticationToken principal);
}
