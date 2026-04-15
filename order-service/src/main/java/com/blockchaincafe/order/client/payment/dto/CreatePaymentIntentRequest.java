package com.blockchaincafe.order.client.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentIntentRequest {
    private String orderId;
    private BigDecimal amount;
    private String payerType;
    private String invoiceNumber;
}
