package com.ing.brokefirm.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Withdraw {
    private Long customerId;
    private BigDecimal amount;
    private String iban;
    private Long version;
}
