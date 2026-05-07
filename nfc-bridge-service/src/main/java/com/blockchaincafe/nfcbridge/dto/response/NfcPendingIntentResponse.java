package com.blockchaincafe.nfcbridge.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class NfcPendingIntentResponse {
    private boolean found;
    private String cardToken;
    private String orderId;
    private BigDecimal amount;
    private String payerType;
    private String message;
}
