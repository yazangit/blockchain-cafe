package com.blockchaincafe.order.client.payment.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentResponse {
    private String id;
    private String orderId;
    private String method;
    private BigDecimal amount;
    private String status;
    private String cryptoRef;
    private Instant createdAt;
    private Instant confirmedAt;
}
