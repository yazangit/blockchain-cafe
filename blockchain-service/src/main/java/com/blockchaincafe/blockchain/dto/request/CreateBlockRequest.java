package com.blockchaincafe.blockchain.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBlockRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String paymentId;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal grossTotal;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal netTotal;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal totalVat;

    @NotBlank
    private String payload;
}
