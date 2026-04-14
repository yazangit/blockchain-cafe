package com.blockchaincafe.order.service;

import com.blockchaincafe.order.dto.request.CheckoutOrderRequest;
import com.blockchaincafe.order.dto.response.CheckoutOrderResponse;

public interface OrderOrchestrationService {
    CheckoutOrderResponse checkout(CheckoutOrderRequest request);
}
