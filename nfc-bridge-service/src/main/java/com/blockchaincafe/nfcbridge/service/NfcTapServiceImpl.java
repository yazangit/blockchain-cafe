package com.blockchaincafe.nfcbridge.service;

import com.blockchaincafe.nfcbridge.dto.request.NfcTapRequest;
import com.blockchaincafe.nfcbridge.dto.response.NfcTapResponse;
import com.blockchaincafe.nfcbridge.store.NfcCheckoutIntentStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NfcTapServiceImpl implements NfcTapService {

    private final NfcCheckoutIntentStore store;

    @Override
    public NfcTapResponse acceptTap(NfcTapRequest request) {
        var matchedIntent = store.remove(request.getCardToken());

        log.info("NFC tap received: cardToken={}, deviceId={}, readerType={}, timestamp={}",
                request.getCardToken(),
                request.getDeviceId(),
                request.getReaderType(),
                request.getTimestamp());

        if (matchedIntent.isPresent()) {
            var intent = matchedIntent.get();

            log.info("NFC tap matched intent and consumed it: cardToken={}, orderId={}, amount={}, payerType={}",
                    intent.getCardToken(),
                    intent.getOrderId(),
                    intent.getAmount(),
                    intent.getPayerType());

            return NfcTapResponse.builder()
                    .accepted(true)
                    .matchedIntent(true)
                    .message("NFC tap matched pending checkout intent")
                    .cardToken(request.getCardToken())
                    .deviceId(request.getDeviceId())
                    .orderId(intent.getOrderId())
                    .amount(intent.getAmount())
                    .payerType(intent.getPayerType())
                    .build();
        }

        return NfcTapResponse.builder()
                .accepted(true)
                .matchedIntent(false)
                .message("NFC tap received but no pending checkout intent was found")
                .cardToken(request.getCardToken())
                .deviceId(request.getDeviceId())
                .build();
    }
}
