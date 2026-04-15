package com.blockchaincafe.vat.service.impl;

import com.blockchaincafe.shared.enums.VatRateType;
import com.blockchaincafe.shared.util.MoneyUtils;
import com.blockchaincafe.vat.domain.model.VatRecordEntity;
import com.blockchaincafe.vat.dto.request.VatCalculationItemRequest;
import com.blockchaincafe.vat.dto.request.VatCalculationRequest;
import com.blockchaincafe.vat.dto.response.VatCalculationResponse;
import com.blockchaincafe.vat.dto.response.VatRecordResponse;
import com.blockchaincafe.vat.exception.VatRecordNotFoundException;
import com.blockchaincafe.vat.repository.VatRecordRepository;
import com.blockchaincafe.vat.service.VatCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VatCalculationServiceImpl implements VatCalculationService {

    private final VatRecordRepository vatRecordRepository;

    @Override
    public VatCalculationResponse calculateAndStore(VatCalculationRequest request) {
        BigDecimal grossTotal = BigDecimal.ZERO;
        BigDecimal netTotal = BigDecimal.ZERO;
        BigDecimal totalVat = BigDecimal.ZERO;
        BigDecimal vat19 = BigDecimal.ZERO;
        BigDecimal vat7 = BigDecimal.ZERO;

        for (VatCalculationItemRequest item : request.getItems()) {
            BigDecimal lineGross = item.getUnitPriceGross().multiply(BigDecimal.valueOf(item.getQuantity()));
            BigDecimal divisor = item.getVatRate() == VatRateType.DE_19
                    ? new BigDecimal("1.19")
                    : new BigDecimal("1.07");

            BigDecimal lineNet = lineGross.divide(divisor, 2, RoundingMode.HALF_UP);
            BigDecimal lineVat = lineGross.subtract(lineNet);

            grossTotal = grossTotal.add(lineGross);
            netTotal = netTotal.add(lineNet);
            totalVat = totalVat.add(lineVat);

            if (item.getVatRate() == VatRateType.DE_19) {
                vat19 = vat19.add(lineVat);
            } else {
                vat7 = vat7.add(lineVat);
            }
        }

        grossTotal = MoneyUtils.normalize(grossTotal);
        netTotal = MoneyUtils.normalize(netTotal);
        totalVat = MoneyUtils.normalize(totalVat);
        vat19 = MoneyUtils.normalize(vat19);
        vat7 = MoneyUtils.normalize(vat7);

        VatRecordEntity entity = VatRecordEntity.builder()
                .id(UUID.randomUUID().toString())
                .orderId(request.getOrderId())
                .payerType(request.getPayerType())
                .invoiceNumber(request.getInvoiceNumber())
                .companyName(request.getCompanyName())
                .vatId(request.getVatId())
                .grossTotal(grossTotal)
                .netTotal(netTotal)
                .totalVat(totalVat)
                .vat19(vat19)
                .vat7(vat7)
                .createdAt(Instant.now())
                .build();

        VatRecordEntity saved = vatRecordRepository.save(entity);

        return VatCalculationResponse.builder()
                .vatRecordId(saved.getId())
                .orderId(saved.getOrderId())
                .payerType(saved.getPayerType())
                .invoiceNumber(saved.getInvoiceNumber())
                .companyName(saved.getCompanyName())
                .vatId(saved.getVatId())
                .grossTotal(saved.getGrossTotal())
                .netTotal(saved.getNetTotal())
                .totalVat(saved.getTotalVat())
                .vat19(saved.getVat19())
                .vat7(saved.getVat7())
                .build();
    }

    @Override
    public VatRecordResponse getByOrderId(String orderId) {
        VatRecordEntity entity = vatRecordRepository.findByOrderId(orderId)
                .orElseThrow(() -> new VatRecordNotFoundException("VAT record not found for orderId: " + orderId));

        return VatRecordResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .payerType(entity.getPayerType())
                .invoiceNumber(entity.getInvoiceNumber())
                .companyName(entity.getCompanyName())
                .vatId(entity.getVatId())
                .grossTotal(entity.getGrossTotal())
                .netTotal(entity.getNetTotal())
                .totalVat(entity.getTotalVat())
                .vat19(entity.getVat19())
                .vat7(entity.getVat7())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
