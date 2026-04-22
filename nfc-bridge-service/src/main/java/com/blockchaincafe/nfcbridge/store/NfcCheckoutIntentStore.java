package com.blockchaincafe.nfcbridge.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NfcCheckoutIntentStore {

    private final Map<String, CheckoutIntent> intentsByCardToken = new ConcurrentHashMap<>();

    public void put(String cardToken, CheckoutIntent intent) {
        intentsByCardToken.put(cardToken, intent);
    }

    public Optional<CheckoutIntent> get(String cardToken) {
        return Optional.ofNullable(intentsByCardToken.get(cardToken));
    }

    public Optional<CheckoutIntent> remove(String cardToken) {
        return Optional.ofNullable(intentsByCardToken.remove(cardToken));
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class CheckoutIntent {
        private String cardToken;
        private String orderId;
        private BigDecimal amount;
        private String payerType;
        private List<Item> items;
        private BusinessDetails businessDetails;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Item {
        private String productId;
        private Integer quantity;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class BusinessDetails {
        private String companyName;
        private String vatId;
    }
}
