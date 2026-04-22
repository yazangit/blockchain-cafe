package com.blockchaincafe.nfcbridge.service;

import com.blockchaincafe.nfcbridge.dto.response.NfcPendingIntentResponse;
import com.blockchaincafe.nfcbridge.store.NfcCheckoutIntentStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NfcCheckoutIntentQueryServiceImpl implements NfcCheckoutIntentQueryService {

    private final NfcCheckoutIntentStore store;

    @Override
    public NfcPendingIntentResponse getPendingIntent(String cardToken) {
        var pending = store.get(cardToken);

        if (pending.isPresent()) {
            var intent = pending.get();
            return NfcPendingIntentResponse.builder()
                    .found(true)
                    .cardToken(intent.getCardToken())
                    .orderId(intent.getOrderId())
                    .amount(intent.getAmount())
                    .payerType(intent.getPayerType())
                    .message("Pending checkout intent found")
                    .build();
        }

        return NfcPendingIntentResponse.builder()
                .found(false)
                .cardToken(cardToken)
                .message("No pending checkout intent found")
                .build();
    }
}
