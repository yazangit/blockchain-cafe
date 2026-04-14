package com.blockchaincafe.wallet.service.impl;

import com.blockchaincafe.shared.util.MoneyUtils;
import com.blockchaincafe.wallet.domain.enums.WalletType;
import com.blockchaincafe.wallet.domain.model.WalletEntity;
import com.blockchaincafe.wallet.dto.request.SettleWalletsRequest;
import com.blockchaincafe.wallet.dto.response.WalletResponse;
import com.blockchaincafe.wallet.dto.response.WalletSettlementResponse;
import com.blockchaincafe.wallet.exception.WalletNotFoundException;
import com.blockchaincafe.wallet.repository.WalletRepository;
import com.blockchaincafe.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public WalletSettlementResponse settle(SettleWalletsRequest request) {
        WalletEntity merchantWallet = walletRepository.findByType(WalletType.MERCHANT_OPERATING)
                .orElseThrow(() -> new WalletNotFoundException("Merchant wallet not found"));

        WalletEntity vatWallet = walletRepository.findByType(WalletType.VAT_RESERVED)
                .orElseThrow(() -> new WalletNotFoundException("VAT wallet not found"));

        merchantWallet.setBalance(MoneyUtils.normalize(
                merchantWallet.getBalance().add(request.getMerchantAmount())
        ));

        vatWallet.setBalance(MoneyUtils.normalize(
                vatWallet.getBalance().add(request.getVatAmount())
        ));

        walletRepository.save(merchantWallet);
        walletRepository.save(vatWallet);

        return WalletSettlementResponse.builder()
                .orderId(request.getOrderId())
                .settled(true)
                .build();
    }

    @Override
    public List<WalletResponse> getAll() {
        return walletRepository.findAll().stream()
                .map(wallet -> WalletResponse.builder()
                        .id(wallet.getId())
                        .type(wallet.getType().name())
                        .balance(wallet.getBalance())
                        .build())
                .toList();
    }
}
