package com.blockchaincafe.order.client.vat.dto;

import com.blockchaincafe.shared.enums.VatRateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VatCalculationItemRequest {
    private String productId;
    private BigDecimal unitPriceGross;
    private VatRateType vatRate;
    private Integer quantity;
}
