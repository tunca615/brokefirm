package com.ing.brokefirm.controller.dto;

import com.ing.brokefirm.enums.OrderSide;
import com.ing.brokefirm.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long customerId;
    private String assetName;
    private OrderSide orderSide;
    private BigDecimal size;
    private BigDecimal price;
}
