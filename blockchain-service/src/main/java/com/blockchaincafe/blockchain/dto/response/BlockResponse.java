package com.blockchaincafe.blockchain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class BlockResponse {
    private String id;
    private Integer blockIndex;
    private String orderId;
    private String paymentId;
    private BigDecimal grossTotal;
    private BigDecimal netTotal;
    private BigDecimal totalVat;
    private String payload;
    private String previousHash;
    private String hash;
    private Instant createdAt;
}
