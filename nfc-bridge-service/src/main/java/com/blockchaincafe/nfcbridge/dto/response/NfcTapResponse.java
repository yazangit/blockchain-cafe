package com.blockchaincafe.nfcbridge.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class NfcTapResponse {
    private boolean accepted;
    private boolean matchedIntent;
    private String message;
    private String cardToken;
    private String deviceId;
    private String orderId;
    private BigDecimal amount;
    private String payerType;
}
