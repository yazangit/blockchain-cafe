package com.blockchaincafe.nfcbridge.client;

import com.blockchaincafe.nfcbridge.client.dto.OrderCheckoutRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderServiceClient {

    private final RestTemplate restTemplate;

    public String checkout(OrderCheckoutRequest request) {
        String url = "http://order-service:8083/api/orders/checkout";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderCheckoutRequest> entity = new HttpEntity<>(request, headers);

        log.info("Calling order-service checkout for orderId={}", request.getOrderId());

        return restTemplate.postForObject(url, entity, String.class);
    }
}
