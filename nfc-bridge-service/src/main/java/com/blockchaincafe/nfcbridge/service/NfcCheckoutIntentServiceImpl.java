package com.blockchaincafe.nfcbridge.service;

import com.blockchaincafe.nfcbridge.dto.request.NfcCheckoutIntentRequest;
import com.blockchaincafe.nfcbridge.dto.response.NfcCheckoutIntentResponse;
import com.blockchaincafe.nfcbridge.store.NfcCheckoutIntentStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NfcCheckoutIntentServiceImpl implements NfcCheckoutIntentService {

    private final NfcCheckoutIntentStore store;

    @Override
    public NfcCheckoutIntentResponse storeIntent(NfcCheckoutIntentRequest request) {
        store.put(
                request.getCardToken(),
                NfcCheckoutIntentStore.CheckoutIntent.builder()
                        .cardToken(request.getCardToken())
                        .orderId(request.getOrderId())
                        .amount(request.getAmount())
                        .payerType(request.getPayerType())
                        .items(
                                request.getItems().stream()
                                        .map(item -> NfcCheckoutIntentStore.Item.builder()
                                                .productId(item.getProductId())
                                                .quantity(item.getQuantity())
                                                .build())
                                        .toList()
                        )
                        .businessDetails(
                                request.getBusinessDetails() == null ? null :
                                        NfcCheckoutIntentStore.BusinessDetails.builder()
                                                .companyName(request.getBusinessDetails().getCompanyName())
                                                .vatId(request.getBusinessDetails().getVatId())
                                                .build()
                        )
                        .build()
        );

        log.info("NFC checkout intent stored: cardToken={}, orderId={}, amount={}, payerType={}, itemCount={}, hasBusinessDetails={}",
                request.getCardToken(),
                request.getOrderId(),
                request.getAmount(),
                request.getPayerType(),
                request.getItems().size(),
                request.getBusinessDetails() != null);

        return NfcCheckoutIntentResponse.builder()
                .stored(true)
                .message("Checkout intent stored successfully")
                .cardToken(request.getCardToken())
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .payerType(request.getPayerType())
                .itemCount(request.getItems().size())
                .hasBusinessDetails(request.getBusinessDetails() != null)
                .build();
    }
}
