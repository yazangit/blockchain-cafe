package com.blockchaincafe.nfcbridge.client.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderCheckoutRequest {
    private String orderId;
    private String payerType;
    private List<Item> items;
    private BusinessDetails businessDetails;

    @Data
    @Builder
    public static class Item {
        private String productId;
        private Integer quantity;
    }

    @Data
    @Builder
    public static class BusinessDetails {
        private String companyName;
        private String vatId;
    }
}
