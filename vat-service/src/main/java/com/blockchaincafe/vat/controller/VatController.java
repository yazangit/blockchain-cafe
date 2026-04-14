package com.blockchaincafe.vat.controller;

import com.blockchaincafe.shared.dto.SuccessResponse;
import com.blockchaincafe.vat.dto.request.VatCalculationRequest;
import com.blockchaincafe.vat.dto.response.VatCalculationResponse;
import com.blockchaincafe.vat.dto.response.VatRecordResponse;
import com.blockchaincafe.vat.service.VatCalculationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vat")
public class VatController {

    private final VatCalculationService vatCalculationService;

    @PostMapping("/calculate")
    public SuccessResponse<VatCalculationResponse> calculate(@Valid @RequestBody VatCalculationRequest request) {
        return SuccessResponse.<VatCalculationResponse>builder()
                .success(true)
                .data(vatCalculationService.calculateAndStore(request))
                .build();
    }

    @GetMapping("/records/{orderId}")
    public SuccessResponse<VatRecordResponse> getByOrderId(@PathVariable("orderId") String orderId) {
        return SuccessResponse.<VatRecordResponse>builder()
                .success(true)
                .data(vatCalculationService.getByOrderId(orderId))
                .build();
    }
}
