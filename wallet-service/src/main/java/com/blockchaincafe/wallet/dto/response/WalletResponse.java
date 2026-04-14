package com.blockchaincafe.wallet.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletResponse {
    private String id;
    private String type;
    private BigDecimal balance;
}
