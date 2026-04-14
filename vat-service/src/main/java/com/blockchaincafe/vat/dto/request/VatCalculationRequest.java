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

    @Valid
    @NotEmpty
    private List<VatCalculationItemRequest> items;
}
