package com.blockchaincafe.order.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutOrderResponse {
    private String orderId;
    private String paymentId;
    private String vatRecordId;
    private String blockId;
}
