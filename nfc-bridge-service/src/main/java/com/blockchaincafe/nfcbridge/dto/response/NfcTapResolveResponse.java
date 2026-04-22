package com.blockchaincafe.nfcbridge.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class NfcTapResolveResponse {
    private boolean accepted;
    private boolean resolved;
    private String resolutionStatus;
    private String message;
    private String cardToken;
    private String deviceId;
    private String orderId;
    private BigDecimal amount;
    private String payerType;
}
