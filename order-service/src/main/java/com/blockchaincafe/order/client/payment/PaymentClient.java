package com.blockchaincafe.order.client.payment;

import com.blockchaincafe.order.client.payment.dto.ConfirmPaymentRequest;
import com.blockchaincafe.order.client.payment.dto.CreatePaymentIntentRequest;
import com.blockchaincafe.order.client.payment.dto.PaymentResponse;
import com.blockchaincafe.shared.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "paymentClient", url = "${app.clients.payment-service-url}")
public interface PaymentClient {

    @PostMapping("/api/payments/intents")
    SuccessResponse<PaymentResponse> createIntent(@RequestBody CreatePaymentIntentRequest request);

    @PostMapping("/api/payments/{paymentId}/confirm")
    SuccessResponse<PaymentResponse> confirm(
            @PathVariable("paymentId") String paymentId,
            @RequestBody ConfirmPaymentRequest request
    );
}
