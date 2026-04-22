package com.blockchaincafe.order.controller;

import com.blockchaincafe.order.dto.response.InvoiceHistoryItemResponse;
import com.blockchaincafe.order.dto.response.InvoicePageResponse;
import com.blockchaincafe.order.dto.response.InvoiceSummaryResponse;
import com.blockchaincafe.order.invoice.InvoicePdfService;
import com.blockchaincafe.order.service.InvoiceHistoryService;
import com.blockchaincafe.shared.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoicePdfService invoicePdfService;
    private final InvoiceHistoryService invoiceHistoryService;

    @GetMapping("/{orderId}")
    public ResponseEntity<Resource> downloadInvoice(@PathVariable("orderId") String orderId) throws Exception {
        Path path = invoicePdfService.getInvoicePath(orderId);

        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(path);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + path.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

   @GetMapping("/history")
public SuccessResponse<InvoicePageResponse<InvoiceHistoryItemResponse>> getInvoiceHistory(
        @RequestParam(name = "payerType", required = false) String payerType,
        @RequestParam(name = "invoiceNumber", required = false) String invoiceNumber,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "sort", defaultValue = "desc") String sort // 🔥 NEW
) {
    return SuccessResponse.<InvoicePageResponse<InvoiceHistoryItemResponse>>builder()
            .success(true)
            .data(invoiceHistoryService.getInvoices(payerType, invoiceNumber, page, size, sort))
            .build();
}

    @GetMapping("/summary")
    public SuccessResponse<InvoiceSummaryResponse> getInvoiceSummary() {
        return SuccessResponse.<InvoiceSummaryResponse>builder()
                .success(true)
                .data(invoiceHistoryService.getSummary())
                .build();
    }

    @DeleteMapping("/history")
    public SuccessResponse<String> clearInvoiceHistory() {
        invoiceHistoryService.clearAll();
        return SuccessResponse.<String>builder()
                .success(true)
                .data("Invoice history cleared successfully")
                .build();
    }
}