package com.blockchaincafe.nfcbridge.service;

import com.blockchaincafe.nfcbridge.dto.request.NfcTapRequest;
import com.blockchaincafe.nfcbridge.dto.response.NfcTapResolveResponse;
import com.blockchaincafe.nfcbridge.state.NfcResolutionState;
import com.blockchaincafe.nfcbridge.store.NfcCheckoutIntentStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.blockchaincafe.nfcbridge.client.OrderServiceClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class NfcTapResolveServiceImpl implements NfcTapResolveService {

    private final NfcCheckoutIntentStore store;
    private final NfcResolutionState resolutionState;
    private final OrderServiceClient orderServiceClient;

    @Override
    public NfcTapResolveResponse tapAndResolve(NfcTapRequest request) {
        var matchedIntent = store.remove(request.getCardToken());

        log.info("NFC tap-and-resolve received: cardToken={}, deviceId={}, readerType={}, timestamp={}",
                request.getCardToken(),
                request.getDeviceId(),
                request.getReaderType(),
                request.getTimestamp());

        NfcTapResolveResponse response;

        if (matchedIntent.isPresent()) {
            var intent = matchedIntent.get();
            // 🔥 NEW: call order-service checkout
try {
    var checkoutRequest = com.blockchaincafe.nfcbridge.client.dto.OrderCheckoutRequest.builder()
            .orderId(intent.getOrderId())
            .payerType(intent.getPayerType())
            .items(
                    intent.getItems().stream()
                            .map(i -> com.blockchaincafe.nfcbridge.client.dto.OrderCheckoutRequest.Item.builder()
                                    .productId(i.getProductId())
                                    .quantity(i.getQuantity())
                                    .build())
                            .toList()
            )
            .businessDetails(
                    intent.getBusinessDetails() == null ? null :
                            com.blockchaincafe.nfcbridge.client.dto.OrderCheckoutRequest.BusinessDetails.builder()
                                    .companyName(intent.getBusinessDetails().getCompanyName())
                                    .vatId(intent.getBusinessDetails().getVatId())
                                    .build()
            )
            .build();

    String orderResponse = orderServiceClient.checkout(checkoutRequest);

    log.info("Order-service checkout response: {}", orderResponse);

} catch (Exception ex) {
    log.error("Order-service checkout failed", ex);
}

            log.info("NFC tap-and-resolve matched intent: cardToken={}, orderId={}, amount={}, payerType={}",
                    intent.getCardToken(),
                    intent.getOrderId(),
                    intent.getAmount(),
                    intent.getPayerType());

            response = NfcTapResolveResponse.builder()
                    .accepted(true)
                    .resolved(true)
                    .resolutionStatus("MATCHED_PENDING_INTENT")
                    .message("NFC tap resolved successfully against pending checkout intent")
                    .cardToken(request.getCardToken())
                    .deviceId(request.getDeviceId())
                    .orderId(intent.getOrderId())
                    .amount(intent.getAmount())
                    .payerType(intent.getPayerType())
                    .build();
        } else {
            response = NfcTapResolveResponse.builder()
                    .accepted(true)
                    .resolved(false)
                    .resolutionStatus("NO_PENDING_INTENT")
                    .message("NFC tap received but no pending checkout intent was found")
                    .cardToken(request.getCardToken())
                    .deviceId(request.getDeviceId())
                    .build();
        }

        resolutionState.setLastResolution(response);
        return response;
    }
}
