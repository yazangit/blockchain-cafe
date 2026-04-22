package com.blockchaincafe.vat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxAnalyticsResponse {
    private long totalRecords;
    private BigDecimal grossTotal;
    private BigDecimal netTotal;
    private BigDecimal totalVat;
    private BigDecimal vat19Total;
    private BigDecimal vat7Total;
    private BigDecimal privateVatTotal;
    private BigDecimal businessVatTotal;
    private List<TaxAnalyticsPointResponse> points;
}