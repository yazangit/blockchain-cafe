package com.blockchaincafe.order.invoice.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InvoiceData {
    private String orderId;
    private String invoiceNumber;
    private String payerType;
    private String companyName;
    private String vatId;
    private String paymentId;
    private BigDecimal grossTotal;
    private BigDecimal netTotal;
    private BigDecimal totalVat;
}
