package com.blockchaincafe.order.client.menu;

import com.blockchaincafe.order.client.menu.dto.ProductResponse;
import com.blockchaincafe.shared.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "menuClient", url = "${app.clients.menu-service-url}")
public interface MenuClient {

    @GetMapping("/api/products/{productId}")
    SuccessResponse<ProductResponse> getProductById(@PathVariable("productId") String productId);
}
