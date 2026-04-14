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
import com.blockchaincafe.order.dto.request.CheckoutOrderRequest;
import com.blockchaincafe.order.dto.request.OrderItemRequest;
import com.blockchaincafe.order.dto.response.CheckoutOrderResponse;
import com.blockchaincafe.order.service.OrderOrchestrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Override
    public CheckoutOrderResponse checkout(CheckoutOrderRequest request) {
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

        PaymentResponse paymentIntent = paymentClient.createIntent(
                new CreatePaymentIntentRequest(
                        request.getOrderId(),
                        grossTotal
                )
        ).getData();

        PaymentResponse confirmedPayment = paymentClient.confirm(
                paymentIntent.getId(),
                new ConfirmPaymentRequest("demo-" + paymentIntent.getId())
        ).getData();

        VatCalculationResponse vat = vatClient.calculate(
                new VatCalculationRequest(
                        request.getOrderId(),
                        vatItems
                )
        ).getData();

        walletClient.settle(new SettleWalletsRequest(
                request.getOrderId(),
                vat.getNetTotal(),
                vat.getTotalVat()
        ));

        CreateBlockResponse block = blockchainClient.createBlock(
                new CreateBlockRequest(
                        request.getOrderId(),
                        confirmedPayment.getId(),
                        vat.getGrossTotal(),
                        vat.getNetTotal(),
                        vat.getTotalVat(),
                        "demo-payload"
                )
        ).getData();

        return CheckoutOrderResponse.builder()
                .orderId(request.getOrderId())
                .paymentId(confirmedPayment.getId())
                .vatRecordId(vat.getVatRecordId())
                .blockId(block.getBlockId())
                .build();
    }
}
