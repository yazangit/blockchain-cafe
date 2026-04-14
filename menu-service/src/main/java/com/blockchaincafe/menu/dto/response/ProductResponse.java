package com.blockchaincafe.menu.dto.response;

import com.blockchaincafe.shared.enums.VatRateType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private BigDecimal priceGross;
    private VatRateType vatRate;
    private Boolean active;
    private Instant createdAt;
}
