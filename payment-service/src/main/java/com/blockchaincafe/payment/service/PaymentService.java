package com.blockchaincafe.payment.service;

import com.blockchaincafe.payment.dto.request.ConfirmPaymentRequest;
import com.blockchaincafe.payment.dto.request.CreatePaymentIntentRequest;
import com.blockchaincafe.payment.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse createIntent(CreatePaymentIntentRequest request);
    PaymentResponse confirm(String paymentId, ConfirmPaymentRequest request);
    PaymentResponse getById(String paymentId);
    PaymentResponse getByOrderId(String orderId);
}
