package com.blockchaincafe.order.controller;

import com.blockchaincafe.order.dto.request.CheckoutOrderRequest;
import com.blockchaincafe.order.dto.response.CheckoutOrderResponse;
import com.blockchaincafe.order.service.OrderOrchestrationService;
import com.blockchaincafe.shared.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderOrchestrationService orderOrchestrationService;

    @PostMapping("/checkout")
    public SuccessResponse<CheckoutOrderResponse> checkout(@Valid @RequestBody CheckoutOrderRequest request) {
        return SuccessResponse.<CheckoutOrderResponse>builder()
                .success(true)
                .data(orderOrchestrationService.checkout(request))
                .build();
    }
}
