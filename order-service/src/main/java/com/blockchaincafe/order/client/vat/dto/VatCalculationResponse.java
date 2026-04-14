package com.blockchaincafe.order.client.vat.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VatCalculationResponse {
    private String vatRecordId;
    private String orderId;
    private BigDecimal grossTotal;
    private BigDecimal netTotal;
    private BigDecimal totalVat;
    private BigDecimal vat19;
    private BigDecimal vat7;
}
