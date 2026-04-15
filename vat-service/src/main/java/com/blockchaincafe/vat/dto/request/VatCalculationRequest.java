package com.blockchaincafe.vat.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VatCalculationRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String payerType;

    @NotBlank
    private String invoiceNumber;

    private String companyName;

    private String vatId;

    @Valid
    @NotEmpty
    private List<VatCalculationItemRequest> items;
}
