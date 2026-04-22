package com.blockchaincafe.order.service;

import com.blockchaincafe.order.dto.response.InvoiceHistoryItemResponse;
import com.blockchaincafe.order.dto.response.InvoicePageResponse;
import com.blockchaincafe.order.dto.response.InvoiceSummaryResponse;

public interface InvoiceHistoryService {
    void saveInvoice(InvoiceHistoryItemResponse invoice);
    InvoicePageResponse<InvoiceHistoryItemResponse> getInvoices(String payerType, String invoiceNumber, int page, int size, String sort);
    InvoiceSummaryResponse getSummary();
    void clearAll();
}