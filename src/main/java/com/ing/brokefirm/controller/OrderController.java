package com.ing.brokefirm.controller;

import com.ing.brokefirm.controller.OrderResource.OrderResource;
import com.ing.brokefirm.controller.dto.OrderDto;
import com.ing.brokefirm.mapper.OrderMapper;
import com.ing.brokefirm.model.Order;
import com.ing.brokefirm.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final OrderMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResource createOrder(@RequestBody OrderDto orderDto, Principal principal) {
        Order createdOrder = orderService.createOrder(mapper.orderDtoToOrder(orderDto), principal);
        return mapper.orderToOrderResource(createdOrder);
    }

    @GetMapping
    public List<OrderResource> listOrders(@RequestParam(required = false) Long customerId,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                          Principal principal) {
        List<Order> orders = orderService.listOrders(customerId, startDate, endDate, principal);
        return orders.stream().map(mapper::orderToOrderResource).toList();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable Long id,
                            Principal principal) {
        orderService.cancelOrder(id, principal);
    }
}
