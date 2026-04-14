package com.blockchaincafe.blockchain.controller;

import com.blockchaincafe.blockchain.dto.request.CreateBlockRequest;
import com.blockchaincafe.blockchain.dto.response.BlockResponse;
import com.blockchaincafe.blockchain.dto.response.CreateBlockResponse;
import com.blockchaincafe.blockchain.service.BlockchainService;
import com.blockchaincafe.shared.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blockchain")
public class BlockchainController {

    private final BlockchainService blockchainService;

    @PostMapping("/blocks")
    public SuccessResponse<CreateBlockResponse> createBlock(@Valid @RequestBody CreateBlockRequest request) {
        return SuccessResponse.<CreateBlockResponse>builder()
                .success(true)
                .data(blockchainService.createBlock(request))
                .build();
    }

    @GetMapping("/validate")
    public SuccessResponse<Boolean> validate() {
        return SuccessResponse.<Boolean>builder()
                .success(true)
                .data(blockchainService.validateChain())
                .build();
    }

    @GetMapping("/blocks")
    public SuccessResponse<List<BlockResponse>> getAll() {
        return SuccessResponse.<List<BlockResponse>>builder()
                .success(true)
                .data(blockchainService.getAllBlocks())
                .build();
    }
}
