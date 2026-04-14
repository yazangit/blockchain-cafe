package com.blockchaincafe.order.client.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettleWalletsRequest {
    private String orderId;
    private BigDecimal merchantAmount;
    private BigDecimal vatAmount;
}
