package com.blockchaincafe.payment.service.impl;

import com.blockchaincafe.payment.domain.enums.PayerType;
import com.blockchaincafe.payment.domain.enums.PaymentMethodType;
import com.blockchaincafe.payment.domain.enums.PaymentStatusType;
import com.blockchaincafe.payment.domain.model.PaymentEntity;
import com.blockchaincafe.payment.dto.request.ConfirmPaymentRequest;
import com.blockchaincafe.payment.dto.request.CreatePaymentIntentRequest;
import com.blockchaincafe.payment.dto.response.PaymentResponse;
import com.blockchaincafe.payment.exception.PaymentNotFoundException;
import com.blockchaincafe.payment.mapper.PaymentMapper;
import com.blockchaincafe.payment.repository.PaymentRepository;
import com.blockchaincafe.payment.service.PaymentService;
import com.blockchaincafe.shared.util.MoneyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse createIntent(CreatePaymentIntentRequest request) {
        PaymentEntity entity = PaymentEntity.builder()
                .id("pay-" + UUID.randomUUID())
                .orderId(request.getOrderId())
                .method(PaymentMethodType.CRYPTO_USDC_SIM)
                .amount(MoneyUtils.normalize(request.getAmount()))
                .status(PaymentStatusType.PENDING)
                .payerType(PayerType.valueOf(request.getPayerType()))
                .invoiceNumber(request.getInvoiceNumber())
                .cryptoRef("intent-" + UUID.randomUUID())
                .createdAt(Instant.now())
                .confirmedAt(null)
                .build();

        return PaymentMapper.toResponse(paymentRepository.save(entity));
    }

    @Override
    public PaymentResponse confirm(String paymentId, ConfirmPaymentRequest request) {
        PaymentEntity entity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + paymentId));

        entity.setStatus(PaymentStatusType.CONFIRMED);
        entity.setCryptoRef(request.getCryptoRef());
        entity.setConfirmedAt(Instant.now());

        return PaymentMapper.toResponse(paymentRepository.save(entity));
    }

    @Override
    public PaymentResponse getById(String paymentId) {
        return paymentRepository.findById(paymentId)
                .map(PaymentMapper::toResponse)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + paymentId));
    }

    @Override
    public PaymentResponse getByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(PaymentMapper::toResponse)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for orderId: " + orderId));
    }
}
