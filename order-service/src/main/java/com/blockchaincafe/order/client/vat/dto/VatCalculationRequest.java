package com.blockchaincafe.order.client.vat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VatCalculationRequest {
    private String orderId;
    private String payerType;
    private String invoiceNumber;
    private String companyName;
    private String vatId;
    private List<VatCalculationItemRequest> items;
}
