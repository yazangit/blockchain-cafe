package com.blockchaincafe.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceHistoryItemResponse {
    private String orderId;
    private String invoiceNumber;
    private String payerType;
    private String companyName;
    private String vatId;
    private String paymentId;
    private BigDecimal grossTotal;
    private BigDecimal netTotal;
    private BigDecimal totalVat;
    private String createdAt;
}