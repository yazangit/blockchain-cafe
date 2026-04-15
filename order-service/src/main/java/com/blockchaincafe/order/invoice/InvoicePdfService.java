package com.blockchaincafe.order.invoice;

import com.blockchaincafe.order.invoice.model.InvoiceData;

import java.nio.file.Path;

public interface InvoicePdfService {
    Path generateInvoicePdf(InvoiceData invoiceData);
    Path getInvoicePath(String orderId);
}
