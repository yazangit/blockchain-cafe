package com.blockchaincafe.nfcbridge.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class NfcCheckoutIntentResponse {
    private boolean stored;
    private String message;
    private String cardToken;
    private String orderId;
    private BigDecimal amount;
    private String payerType;
    private int itemCount;
    private boolean hasBusinessDetails;
}
