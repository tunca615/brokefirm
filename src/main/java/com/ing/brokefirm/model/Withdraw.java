package com.ing.brokefirm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Withdraw {
    private Long customerId;
    private BigDecimal amount;
    private String iban;
}
