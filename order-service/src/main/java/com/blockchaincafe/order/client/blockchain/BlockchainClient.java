package com.blockchaincafe.order.client.blockchain;

import com.blockchaincafe.order.client.blockchain.dto.CreateBlockRequest;
import com.blockchaincafe.order.client.blockchain.dto.CreateBlockResponse;
import com.blockchaincafe.shared.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "blockchainClient", url = "${app.clients.blockchain-service-url}")
public interface BlockchainClient {

    @PostMapping("/api/blockchain/blocks")
    SuccessResponse<CreateBlockResponse> createBlock(@RequestBody CreateBlockRequest request);
}
