package com.ing.brokefirm.service.impl;

import com.ing.brokefirm.constant.Constant;
import com.ing.brokefirm.enums.OrderStatus;
import com.ing.brokefirm.exception.InsufficientBalanceException;
import com.ing.brokefirm.mapper.OrderMapper;
import com.ing.brokefirm.model.Asset;
import com.ing.brokefirm.model.Customer;
import com.ing.brokefirm.model.Deposit;
import com.ing.brokefirm.model.Order;
import com.ing.brokefirm.repository.OrderRepository;
import com.ing.brokefirm.repository.entity.OrderEntity;
import com.ing.brokefirm.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final AssetService assetService;
    private final OrderMapper orderMapper;
    private final CustomerService customerService;

    public Order createOrder(Order order, Principal principal) {
        Customer customer = customerService.findByUsername(principal.getName());
        if(!customer.getRole().equals("ADMIN")){
            order.setCustomerId(customer.getId());
        }
        validateAssetName(order);

        List<Asset> assetList = assetService.listAssetsByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName());
        validateAsset(order, assetList);

        return orderMapper.orderEntityToOrder(orderRepository.save(orderMapper.orderToOrderEntity(order)));
    }

    private static void validateAssetName(Order order) {
        if (!order.getAssetName().equals(Constant.TRY)) {
            throw new IllegalArgumentException("Only support TRY asset name");
        }
    }

    private static void validateAsset(Order order, List<Asset> assetList) {
        if (assetList.isEmpty()) {
            throw new IllegalArgumentException(String.format("Customer do not have '%s' asset name", order.getAssetName()));
        }
        if (assetList.get(0).getUsableSize().compareTo(order.totalAmount()) < 0) {
            throw new IllegalArgumentException("Customer do not have enough usable money");
        }
    }

    public List<Order> listOrders(Long customerId, LocalDateTime startDate, LocalDateTime endDate, Principal principal) {
        Customer customer = customerService.findByUsername(principal.getName());
        if(!customer.getRole().equals(Constant.ADMIN)){
            customerId = customer.getId();
        }
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate).stream().map(orderMapper::orderEntityToOrder).toList();
    }

    public void cancelOrder(Long orderId, Principal principal) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new InsufficientBalanceException("Order Id do not have this system"));
        Order order = orderMapper.orderEntityToOrder(orderEntity);

        validateCancelOrder(principal, order);

        order.setStatus(OrderStatus.CANCELED);
        assetService.depositMoney(Deposit.builder().amount(order.totalAmount()).customerId(order.getCustomerId()).build(), principal);
    }

    private void validateCancelOrder(Principal principal, Order order) {
        Customer customer = customerService.findByUsername(principal.getName());
        if(!order.getCustomerId().equals(customer.getId()) && !customer.getRole().equals(Constant.ADMIN)){
            throw new IllegalArgumentException("Order Id do not have this system");
        }
        if (order.getStatus().equals(OrderStatus.PENDING)) {
            throw new IllegalArgumentException("Only pending status order can be canceled");
        }
    }
}

