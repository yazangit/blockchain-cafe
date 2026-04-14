package com.blockchaincafe.wallet.service;

import com.blockchaincafe.wallet.dto.request.SettleWalletsRequest;
import com.blockchaincafe.wallet.dto.response.WalletResponse;
import com.blockchaincafe.wallet.dto.response.WalletSettlementResponse;

import java.util.List;

public interface WalletService {
    WalletSettlementResponse settle(SettleWalletsRequest request);
    List<WalletResponse> getAll();
}
