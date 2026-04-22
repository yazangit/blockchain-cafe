package com.blockchaincafe.blockchain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockValidationResponse {
    private boolean valid;
    private String message;
    private int chainSize;
    private Integer invalidBlockIndex;
    private String lastBlockHash;
    private String storedHash;
    private String recalculatedHash;
    private String expectedPreviousHash;
    private String actualPreviousHash;
}