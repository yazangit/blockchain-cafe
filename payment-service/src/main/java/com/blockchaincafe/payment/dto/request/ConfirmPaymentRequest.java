package com.blockchaincafe.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConfirmPaymentRequest {

    @NotBlank
    private String cryptoRef;
}
