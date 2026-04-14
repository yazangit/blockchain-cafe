package com.blockchaincafe.vat.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class VatRecordResponse {
    private String id;
    private String orderId;
    private BigDecimal grossTotal;
    private BigDecimal netTotal;
    private BigDecimal totalVat;
    private BigDecimal vat19;
    private BigDecimal vat7;
    private Instant createdAt;
}
