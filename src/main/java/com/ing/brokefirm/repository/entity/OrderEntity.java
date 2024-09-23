package com.ing.brokefirm.repository.entity;

import com.ing.brokefirm.enums.OrderStatus;
import com.ing.brokefirm.enums.OrderSide;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String assetName;

    @Enumerated(EnumType.STRING)
    private OrderSide orderSide;
    private BigDecimal size;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime createDate;
}
