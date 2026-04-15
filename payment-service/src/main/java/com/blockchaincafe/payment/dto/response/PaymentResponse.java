package com.blockchaincafe.payment.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class PaymentResponse {
    private String id;
    private String orderId;
    private String method;
    private BigDecimal amount;
    private String status;
    private String payerType;
    private String invoiceNumber;
    private String cryptoRef;
    private Instant createdAt;
    private Instant confirmedAt;
}
