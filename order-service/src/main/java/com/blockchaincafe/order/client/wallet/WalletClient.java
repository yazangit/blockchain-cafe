package com.blockchaincafe.order.client.wallet;

import com.blockchaincafe.order.client.wallet.dto.SettleWalletsRequest;
import com.blockchaincafe.shared.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "walletClient", url = "${app.clients.wallet-service-url}")
public interface WalletClient {

    @PostMapping("/api/wallets/settle")
    SuccessResponse<Object> settle(@RequestBody SettleWalletsRequest request);
}
