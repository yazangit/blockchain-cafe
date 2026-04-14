package com.blockchaincafe.order.client.menu.dto;

import com.blockchaincafe.shared.enums.VatRateType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private BigDecimal priceGross;
    private VatRateType vatRate;
    private Boolean active;
    private Instant createdAt;
}
