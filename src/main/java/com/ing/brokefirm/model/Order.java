package com.ing.brokefirm.model;

import com.ing.brokefirm.enums.OrderSide;
import com.ing.brokefirm.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private Long customerId;
    private String assetName;
    private OrderSide orderSide;
    private BigDecimal size;
    private BigDecimal price;
    private OrderStatus status;
    private LocalDateTime createDate;

    public BigDecimal totalAmount() {
        return getPrice().multiply(getSize());
    }
}
