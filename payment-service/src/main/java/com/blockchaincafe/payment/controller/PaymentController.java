package com.blockchaincafe.payment.controller;

import com.blockchaincafe.payment.dto.request.ConfirmPaymentRequest;
import com.blockchaincafe.payment.dto.request.CreatePaymentIntentRequest;
import com.blockchaincafe.payment.dto.response.PaymentResponse;
import com.blockchaincafe.payment.service.PaymentService;
import com.blockchaincafe.shared.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/intents")
    public SuccessResponse<PaymentResponse> createIntent(@Valid @RequestBody CreatePaymentIntentRequest request) {
        return SuccessResponse.<PaymentResponse>builder()
                .success(true)
                .data(paymentService.createIntent(request))
                .build();
    }

    @PostMapping("/{paymentId}/confirm")
    public SuccessResponse<PaymentResponse> confirm(
            @PathVariable("paymentId") String paymentId,
            @Valid @RequestBody ConfirmPaymentRequest request
    ) {
        return SuccessResponse.<PaymentResponse>builder()
                .success(true)
                .data(paymentService.confirm(paymentId, request))
                .build();
    }

    @GetMapping("/{paymentId}")
    public SuccessResponse<PaymentResponse> getById(@PathVariable("paymentId") String paymentId) {
        return SuccessResponse.<PaymentResponse>builder()
                .success(true)
                .data(paymentService.getById(paymentId))
                .build();
    }

    @GetMapping("/by-order/{orderId}")
    public SuccessResponse<PaymentResponse> getByOrderId(@PathVariable("orderId") String orderId) {
        return SuccessResponse.<PaymentResponse>builder()
                .success(true)
                .data(paymentService.getByOrderId(orderId))
                .build();
    }
}
