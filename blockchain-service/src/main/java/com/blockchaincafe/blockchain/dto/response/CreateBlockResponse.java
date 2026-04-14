package com.blockchaincafe.blockchain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBlockResponse {
    private String blockId;
    private Integer blockIndex;
    private String hash;
    private String previousHash;
}
