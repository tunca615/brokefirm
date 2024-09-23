package com.ing.brokefirm.service.impl;

import com.ing.brokefirm.constant.Constant;
import com.ing.brokefirm.enums.OrderSide;
import com.ing.brokefirm.enums.OrderStatus;
import com.ing.brokefirm.exception.InsufficientBalanceException;
import com.ing.brokefirm.mapper.OrderMapper;
import com.ing.brokefirm.model.Asset;
import com.ing.brokefirm.model.Customer;
import com.ing.brokefirm.model.Order;
import com.ing.brokefirm.repository.OrderRepository;
import com.ing.brokefirm.repository.entity.OrderEntity;
import com.ing.brokefirm.service.IOrderService;
import com.ing.brokefirm.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final AssetService assetService;
    private final OrderMapper orderMapper;
    private final CustomerService customerService;

    public Order createOrder(Order order, UsernamePasswordAuthenticationToken principal) {
        if (Util.isAdmin(principal)) {
            Customer customer = customerService.findByUsername(principal.getName());
            order.setCustomerId(customer.getId());
        }

        if(order.getOrderSide().equals(OrderSide.SELL)){
            List<Asset> assetList = assetService.listAssetsByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName());
            validateAsset(order, assetList);
        }

        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(LocalDateTime.now());
        if(order.getOrderSide().equals(OrderSide.BUY)){
            assetService.blockSize(order.getCustomerId(), order.getAssetName(), order.totalAmount());
        }
        return orderMapper.orderEntityToOrder(orderRepository.save(orderMapper.orderToOrderEntity(order)));
    }

    private static void validateAsset(Order order, List<Asset> assetList) {
        if (assetList.isEmpty()) {
            throw new IllegalArgumentException(String.format("Customer do not have '%s' asset", order.getAssetName()));
        }
        if (assetList.get(0).getUsableSize().compareTo(order.totalAmount()) < 0) {
            throw new IllegalArgumentException("Customer do not have enough sellable asset");
        }
    }

    public List<Order> listOrders(Long customerId, LocalDateTime startDate, LocalDateTime endDate, UsernamePasswordAuthenticationToken principal) {
        Customer customer = customerService.findByUsername(principal.getName());
        if (!customer.getRole().equals(Constant.ADMIN)) {
            customerId = customer.getId();
        }
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate).stream().map(orderMapper::orderEntityToOrder).toList();
    }

    public void cancelOrder(Long orderId, UsernamePasswordAuthenticationToken principal) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new InsufficientBalanceException("Order Id do not have this system"));
        Order order = orderMapper.orderEntityToOrder(orderEntity);

        validateCancelOrder(principal, order);

        order.setStatus(OrderStatus.CANCELED);
        assetService.releaseSize(order.getCustomerId(), order.getAssetName(), order.totalAmount());
    }

    private void validateCancelOrder(UsernamePasswordAuthenticationToken principal, Order order) {
        Customer customer = customerService.findByUsername(principal.getName());
        if (!order.getCustomerId().equals(customer.getId()) && !customer.getRole().equals(Constant.ADMIN)) {
            throw new IllegalArgumentException("Order Id do not have this system");
        }
        if (order.getStatus().equals(OrderStatus.PENDING)) {
            throw new IllegalArgumentException("Only pending status order can be canceled");
        }
    }
}

