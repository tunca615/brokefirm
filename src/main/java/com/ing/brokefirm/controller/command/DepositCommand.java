package com.ing.brokefirm.controller.command;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositCommand {
    private Long customerId;
    private BigDecimal amount;
}
