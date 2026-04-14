package com.blockchaincafe.order.client.blockchain.dto;

import lombok.Data;

@Data
public class CreateBlockResponse {
    private String blockId;
    private Integer blockIndex;
    private String hash;
    private String previousHash;
}
