package com.blockchaincafe.order.client.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBlockRequest {
    private String orderId;
    private String paymentId;
    private BigDecimal grossTotal;
    private BigDecimal netTotal;
    private BigDecimal totalVat;
    private String payload;
}
