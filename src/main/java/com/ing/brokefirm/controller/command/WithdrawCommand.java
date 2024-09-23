package com.ing.brokefirm.controller.command;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawCommand {
    private Long customerId;
    private BigDecimal amount;
    private String iban;
    private Long version;
}
