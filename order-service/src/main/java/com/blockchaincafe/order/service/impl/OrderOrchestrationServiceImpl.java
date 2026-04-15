package com.blockchaincafe.order.service.impl;

import com.blockchaincafe.order.client.blockchain.BlockchainClient;
import com.blockchaincafe.order.client.blockchain.dto.CreateBlockRequest;
import com.blockchaincafe.order.client.blockchain.dto.CreateBlockResponse;
import com.blockchaincafe.order.client.menu.MenuClient;
import com.blockchaincafe.order.client.menu.dto.ProductResponse;
import com.blockchaincafe.order.client.payment.PaymentClient;
import com.blockchaincafe.order.client.payment.dto.ConfirmPaymentRequest;
import com.blockchaincafe.order.client.payment.dto.CreatePaymentIntentRequest;
import com.blockchaincafe.order.client.payment.dto.PaymentResponse;
import com.blockchaincafe.order.client.vat.VatClient;
import com.blockchaincafe.order.client.vat.dto.VatCalculationItemRequest;
import com.blockchaincafe.order.client.vat.dto.VatCalculationRequest;
import com.blockchaincafe.order.client.vat.dto.VatCalculationResponse;
import com.blockchaincafe.order.client.wallet.WalletClient;
import com.blockchaincafe.order.client.wallet.dto.SettleWalletsRequest;
import com.blockchaincafe.order.dto.request.BusinessDetailsRequest;
import com.blockchaincafe.order.dto.request.CheckoutOrderRequest;
import com.blockchaincafe.order.dto.request.OrderItemRequest;
import com.blockchaincafe.order.dto.response.CheckoutOrderResponse;
import com.blockchaincafe.order.invoice.InvoicePdfService;
import com.blockchaincafe.order.invoice.model.InvoiceData;
import com.blockchaincafe.order.service.OrderOrchestrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderOrchestrationServiceImpl implements OrderOrchestrationService {

    private final MenuClient menuClient;
    private final PaymentClient paymentClient;
    private final VatClient vatClient;
    private final WalletClient walletClient;
    private final BlockchainClient blockchainClient;
    private final InvoicePdfService invoicePdfService;

    @Override
    public CheckoutOrderResponse checkout(CheckoutOrderRequest request) {
        validatePayerType(request);

        BigDecimal grossTotal = BigDecimal.ZERO;
        List<VatCalculationItemRequest> vatItems = new ArrayList<>();

        for (OrderItemRequest item : request.getItems()) {
            ProductResponse product = menuClient.getProductById(item.getProductId()).getData();

            BigDecimal lineTotal = product.getPriceGross()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            grossTotal = grossTotal.add(lineTotal);

            vatItems.add(new VatCalculationItemRequest(
                    product.getId(),
                    product.getPriceGross(),
                    product.getVatRate(),
                    item.getQuantity()
            ));
        }

        String invoiceNumber = generateInvoiceNumber(request);

        PaymentResponse paymentIntent = paymentClient.createIntent(
                new CreatePaymentIntentRequest(
                        request.getOrderId(),
                        grossTotal,
                        request.getPayerType(),
                        invoiceNumber
                )
        ).getData();

        PaymentResponse confirmedPayment = paymentClient.confirm(
                paymentIntent.getId(),
                new ConfirmPaymentRequest("demo-" + paymentIntent.getId())
        ).getData();

        VatCalculationResponse vat = vatClient.calculate(
                new VatCalculationRequest(
                        request.getOrderId(),
                        request.getPayerType(),
                        invoiceNumber,
                        request.getBusinessDetails() != null ? request.getBusinessDetails().getCompanyName() : null,
                        request.getBusinessDetails() != null ? request.getBusinessDetails().getVatId() : null,
                        vatItems
                )
        ).getData();

        walletClient.settle(new SettleWalletsRequest(
                request.getOrderId(),
                vat.getNetTotal(),
                vat.getTotalVat()
        ));

        String payload = buildPayload(request, confirmedPayment.getId(), vat, invoiceNumber);

        CreateBlockResponse block = blockchainClient.createBlock(
                new CreateBlockRequest(
                        request.getOrderId(),
                        confirmedPayment.getId(),
                        vat.getGrossTotal(),
                        vat.getNetTotal(),
                        vat.getTotalVat(),
                        payload
                )
        ).getData();

        invoicePdfService.generateInvoicePdf(
                InvoiceData.builder()
                        .orderId(request.getOrderId())
                        .invoiceNumber(invoiceNumber)
                        .payerType(request.getPayerType())
                        .companyName(request.getBusinessDetails() != null ? request.getBusinessDetails().getCompanyName() : null)
                        .vatId(request.getBusinessDetails() != null ? request.getBusinessDetails().getVatId() : null)
                        .paymentId(confirmedPayment.getId())
                        .grossTotal(vat.getGrossTotal())
                        .netTotal(vat.getNetTotal())
                        .totalVat(vat.getTotalVat())
                        .build()
        );

        return CheckoutOrderResponse.builder()
                .orderId(request.getOrderId())
                .paymentId(confirmedPayment.getId())
                .vatRecordId(vat.getVatRecordId())
                .blockId(block.getBlockId())
                .payerType(request.getPayerType())
                .invoiceNumber(invoiceNumber)
                .companyName(
                        request.getBusinessDetails() != null ? request.getBusinessDetails().getCompanyName() : null
                )
                .vatId(
                        request.getBusinessDetails() != null ? request.getBusinessDetails().getVatId() : null
                )
                .build();
    }

    private void validatePayerType(CheckoutOrderRequest request) {
        if (request.getPayerType() == null || request.getPayerType().isBlank()) {
            throw new IllegalArgumentException("payerType is required");
        }

        if (!"PRIVATE".equals(request.getPayerType()) && !"BUSINESS".equals(request.getPayerType())) {
            throw new IllegalArgumentException("payerType must be PRIVATE or BUSINESS");
        }

        if ("BUSINESS".equals(request.getPayerType())) {
            BusinessDetailsRequest details = request.getBusinessDetails();

            if (details == null) {
                throw new IllegalArgumentException("businessDetails is required when payerType is BUSINESS");
            }

            if (details.getCompanyName() == null || details.getCompanyName().isBlank()) {
                throw new IllegalArgumentException("companyName is required for BUSINESS payerType");
            }

            if (details.getVatId() == null || details.getVatId().isBlank()) {
                throw new IllegalArgumentException("vatId is required for BUSINESS payerType");
            }
        }
    }

    private String generateInvoiceNumber(CheckoutOrderRequest request) {
        String year = String.valueOf(LocalDate.now().getYear());
        String suffix = request.getOrderId().replaceAll("[^A-Za-z0-9]", "");
        if (suffix.length() > 12) {
            suffix = suffix.substring(suffix.length() - 12);
        }
        return "INV-" + year + "-" + suffix.toUpperCase();
    }

    private String buildPayload(
            CheckoutOrderRequest request,
            String paymentId,
            VatCalculationResponse vat,
            String invoiceNumber
    ) {
        if ("BUSINESS".equals(request.getPayerType())) {
            return """
                    {
                      "orderId":"%s",
                      "payerType":"%s",
                      "invoiceNumber":"%s",
                      "companyName":"%s",
                      "vatId":"%s",
                      "paymentId":"%s",
                      "grossTotal":"%s",
                      "netTotal":"%s",
                      "totalVat":"%s"
                    }
                    """.formatted(
                    request.getOrderId(),
                    request.getPayerType(),
                    invoiceNumber,
                    request.getBusinessDetails().getCompanyName(),
                    request.getBusinessDetails().getVatId(),
                    paymentId,
                    vat.getGrossTotal(),
                    vat.getNetTotal(),
                    vat.getTotalVat()
            );
        }

        return """
                {
                  "orderId":"%s",
                  "payerType":"%s",
                  "invoiceNumber":"%s",
                  "paymentId":"%s",
                  "grossTotal":"%s",
                  "netTotal":"%s",
                  "totalVat":"%s"
                }
                """.formatted(
                request.getOrderId(),
                request.getPayerType(),
                invoiceNumber,
                paymentId,
                vat.getGrossTotal(),
                vat.getNetTotal(),
                vat.getTotalVat()
        );
    }
}
