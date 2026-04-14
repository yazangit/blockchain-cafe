package com.blockchaincafe.wallet.dto.request;

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
public class SettleWalletsRequest {

    @NotBlank
    private String orderId;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal merchantAmount;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal vatAmount;
}
