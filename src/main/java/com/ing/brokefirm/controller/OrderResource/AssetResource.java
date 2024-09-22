package com.ing.brokefirm.controller.OrderResource;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetResource {
    private Long id;
    private Long customerId;
    private String assetName;
    private BigDecimal size;
    private BigDecimal usableSize;
}
