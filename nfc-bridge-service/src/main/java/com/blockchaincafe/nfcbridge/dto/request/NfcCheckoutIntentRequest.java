package com.blockchaincafe.nfcbridge.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class NfcCheckoutIntentRequest {

    @NotBlank
    private String cardToken;

    @NotBlank
    private String orderId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotBlank
    private String payerType;

    @Valid
    @NotEmpty
    private List<Item> items;

    @Valid
    private BusinessDetails businessDetails;

    @Data
    public static class Item {
        @NotBlank
        private String productId;

        @NotNull
        private Integer quantity;
    }

    @Data
    public static class BusinessDetails {
        @NotBlank
        private String companyName;

        @NotBlank
        private String vatId;
    }
}
