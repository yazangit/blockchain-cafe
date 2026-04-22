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
public class InvoiceSummaryResponse {
    private long count;
    private BigDecimal totalAmount;
    private BigDecimal averageAmount;
    private String lastInvoiceNumber;
    private long privateCount;
    private long businessCount;
}