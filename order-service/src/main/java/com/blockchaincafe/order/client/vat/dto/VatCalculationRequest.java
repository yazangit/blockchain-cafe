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
    private List<VatCalculationItemRequest> items;
}
