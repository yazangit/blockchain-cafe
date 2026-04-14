package com.blockchaincafe.wallet.controller;

import com.blockchaincafe.shared.dto.SuccessResponse;
import com.blockchaincafe.wallet.dto.request.SettleWalletsRequest;
import com.blockchaincafe.wallet.dto.response.WalletResponse;
import com.blockchaincafe.wallet.dto.response.WalletSettlementResponse;
import com.blockchaincafe.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/settle")
    public SuccessResponse<WalletSettlementResponse> settle(@Valid @RequestBody SettleWalletsRequest request) {
        return SuccessResponse.<WalletSettlementResponse>builder()
                .success(true)
                .data(walletService.settle(request))
                .build();
    }

    @GetMapping
    public SuccessResponse<List<WalletResponse>> getAll() {
        return SuccessResponse.<List<WalletResponse>>builder()
                .success(true)
                .data(walletService.getAll())
                .build();
    }
}
