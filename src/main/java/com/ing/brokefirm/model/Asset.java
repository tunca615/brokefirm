package com.ing.brokefirm.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Asset {
    private Long id;
    private Long customerId;
    private String assetName;
    private BigDecimal size;
    private BigDecimal usableSize;
}
