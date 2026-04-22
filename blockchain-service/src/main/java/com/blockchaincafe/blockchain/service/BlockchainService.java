package com.blockchaincafe.blockchain.service;

import com.blockchaincafe.blockchain.dto.request.CreateBlockRequest;
import com.blockchaincafe.blockchain.dto.response.BlockResponse;
import com.blockchaincafe.blockchain.dto.response.BlockValidationResponse;
import com.blockchaincafe.blockchain.dto.response.BlockchainOverviewResponse;
import com.blockchaincafe.blockchain.dto.response.CreateBlockResponse;

import java.util.List;

public interface BlockchainService {
    CreateBlockResponse createBlock(CreateBlockRequest request);
    boolean validateChain();
    BlockValidationResponse validateChainDetailed();
    List<BlockResponse> getAllBlocks();
    BlockResponse getLatestBlock();
    BlockResponse getBlockByOrderId(String orderId);
    BlockchainOverviewResponse getOverview();
}