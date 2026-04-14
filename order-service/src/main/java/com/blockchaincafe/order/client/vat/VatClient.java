package com.blockchaincafe.order.client.vat;

import com.blockchaincafe.order.client.vat.dto.VatCalculationRequest;
import com.blockchaincafe.order.client.vat.dto.VatCalculationResponse;
import com.blockchaincafe.shared.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "vatClient", url = "${app.clients.vat-service-url}")
public interface VatClient {

    @PostMapping("/api/vat/calculate")
    SuccessResponse<VatCalculationResponse> calculate(@RequestBody VatCalculationRequest request);
}
