package com.blockchaincafe.wallet.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletSettlementResponse {
    private String orderId;
    private boolean settled;
}
