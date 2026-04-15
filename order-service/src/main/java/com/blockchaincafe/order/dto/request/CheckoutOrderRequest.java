package com.blockchaincafe.order.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CheckoutOrderRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String payerType; // PRIVATE or BUSINESS

    @Valid
    private BusinessDetailsRequest businessDetails;

    @Valid
    @NotEmpty
    private List<OrderItemRequest> items;
}
