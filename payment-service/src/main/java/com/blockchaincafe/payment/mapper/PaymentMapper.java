package com.blockchaincafe.payment.mapper;

import com.blockchaincafe.payment.domain.model.PaymentEntity;
import com.blockchaincafe.payment.dto.response.PaymentResponse;

public final class PaymentMapper {

    private PaymentMapper() {
    }

    public static PaymentResponse toResponse(PaymentEntity entity) {
        return PaymentResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .method(entity.getMethod().name())
                .amount(entity.getAmount())
                .status(entity.getStatus().name())
                .cryptoRef(entity.getCryptoRef())
                .createdAt(entity.getCreatedAt())
                .confirmedAt(entity.getConfirmedAt())
                .build();
    }
}
