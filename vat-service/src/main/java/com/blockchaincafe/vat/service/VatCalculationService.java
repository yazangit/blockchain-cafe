package com.blockchaincafe.vat.service;

import com.blockchaincafe.vat.dto.request.VatCalculationRequest;
import com.blockchaincafe.vat.dto.response.TaxAnalyticsResponse;
import com.blockchaincafe.vat.dto.response.VatCalculationResponse;
import com.blockchaincafe.vat.dto.response.VatRecordResponse;

public interface VatCalculationService {
    VatCalculationResponse calculateAndStore(VatCalculationRequest request);
    VatRecordResponse getByOrderId(String orderId);
    TaxAnalyticsResponse getTaxAnalytics();
}