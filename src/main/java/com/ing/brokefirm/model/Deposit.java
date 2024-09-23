package com.ing.brokefirm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {
    private Long customerId;
    private BigDecimal amount;
    private Long version;
}
