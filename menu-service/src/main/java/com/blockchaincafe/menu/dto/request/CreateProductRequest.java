package com.blockchaincafe.menu.dto.request;

import com.blockchaincafe.shared.enums.VatRateType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal priceGross;

    @NotNull
    private VatRateType vatRate;

    @NotNull
    private Boolean active;
}
