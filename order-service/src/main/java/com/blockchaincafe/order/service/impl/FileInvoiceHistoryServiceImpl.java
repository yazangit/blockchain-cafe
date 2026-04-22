package com.blockchaincafe.order.service.impl;

import com.blockchaincafe.order.dto.response.InvoiceHistoryItemResponse;
import com.blockchaincafe.order.dto.response.InvoicePageResponse;
import com.blockchaincafe.order.dto.response.InvoiceSummaryResponse;
import com.blockchaincafe.order.service.InvoiceHistoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class FileInvoiceHistoryServiceImpl implements InvoiceHistoryService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path storagePath = Paths.get("data", "invoice-history.json");

    @Override
    public synchronized void saveInvoice(InvoiceHistoryItemResponse invoice) {
        List<InvoiceHistoryItemResponse> invoices = readInvoices();

        invoices.removeIf(existing -> existing.getOrderId() != null
                && existing.getOrderId().equals(invoice.getOrderId()));

        invoices.add(0, invoice);
        invoices.sort(Comparator.comparing(
                InvoiceHistoryItemResponse::getCreatedAt,
                Comparator.nullsLast(Comparator.reverseOrder())
        ));

        writeInvoices(invoices);
    }

 @Override
public synchronized InvoicePageResponse<InvoiceHistoryItemResponse> getInvoices(
        String payerType,
        String invoiceNumber,
        int page,
        int size,
        String sort
) {
    int safePage = Math.max(page, 0);
    int safeSize = Math.max(size, 1);

    Comparator<InvoiceHistoryItemResponse> comparator =
            Comparator.comparing(
                    InvoiceHistoryItemResponse::getCreatedAt,
                    Comparator.nullsLast(String::compareTo)
            );

    // 🔥 SORT LOGIC
    if ("asc".equalsIgnoreCase(sort)) {
        comparator = comparator;
    } else {
        comparator = comparator.reversed(); // default desc
    }

    List<InvoiceHistoryItemResponse> filtered = readInvoices().stream()
            .sorted(comparator)
            .filter(invoice -> matchesPayerType(invoice, payerType))
            .filter(invoice -> matchesInvoiceNumber(invoice, invoiceNumber))
            .toList();

    long totalElements = filtered.size();
    int totalPages = (int) Math.ceil(totalElements / (double) safeSize);

    int fromIndex = safePage * safeSize;
    int toIndex = Math.min(fromIndex + safeSize, filtered.size());

    List<InvoiceHistoryItemResponse> content;
    if (fromIndex >= filtered.size()) {
        content = new ArrayList<>();
    } else {
        content = filtered.subList(fromIndex, toIndex);
    }

    return InvoicePageResponse.<InvoiceHistoryItemResponse>builder()
            .content(content)
            .page(safePage)
            .size(safeSize)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .first(safePage == 0)
            .last(totalPages == 0 || safePage >= totalPages - 1)
            .empty(content.isEmpty())
            .build();
}

    @Override
    public synchronized InvoiceSummaryResponse getSummary() {
        List<InvoiceHistoryItemResponse> invoices = readInvoices().stream()
                .sorted(Comparator.comparing(
                        InvoiceHistoryItemResponse::getCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .collect(Collectors.toList());

        BigDecimal total = invoices.stream()
                .map(i -> i.getGrossTotal() == null ? BigDecimal.ZERO : i.getGrossTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long count = invoices.size();

        BigDecimal average = count == 0
                ? BigDecimal.ZERO
                : total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);

        long privateCount = invoices.stream()
                .filter(i -> "PRIVATE".equalsIgnoreCase(i.getPayerType()))
                .count();

        long businessCount = invoices.stream()
                .filter(i -> "BUSINESS".equalsIgnoreCase(i.getPayerType()))
                .count();

        String lastInvoiceNumber = invoices.isEmpty() ? "-" : invoices.get(0).getInvoiceNumber();

        return InvoiceSummaryResponse.builder()
                .count(count)
                .totalAmount(total.setScale(2, RoundingMode.HALF_UP))
                .averageAmount(average)
                .lastInvoiceNumber(lastInvoiceNumber)
                .privateCount(privateCount)
                .businessCount(businessCount)
                .build();
    }

    @Override
    public synchronized void clearAll() {
        writeInvoices(new ArrayList<>());
    }

    private boolean matchesPayerType(InvoiceHistoryItemResponse invoice, String payerType) {
        if (payerType == null || payerType.isBlank()) {
            return true;
        }
        return payerType.trim().equalsIgnoreCase(invoice.getPayerType());
    }

    private boolean matchesInvoiceNumber(InvoiceHistoryItemResponse invoice, String invoiceNumber) {
        if (invoiceNumber == null || invoiceNumber.isBlank()) {
            return true;
        }

        String filter = invoiceNumber.trim().toLowerCase(Locale.ROOT);
        String current = invoice.getInvoiceNumber() == null
                ? ""
                : invoice.getInvoiceNumber().toLowerCase(Locale.ROOT);

        return current.contains(filter);
    }

    private List<InvoiceHistoryItemResponse> readInvoices() {
        try {
            ensureStorageExists();

            if (!Files.exists(storagePath) || Files.size(storagePath) == 0) {
                return new ArrayList<>();
            }

            return objectMapper.readValue(
                    storagePath.toFile(),
                    new TypeReference<List<InvoiceHistoryItemResponse>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to read invoice history file", e);
        }
    }

    private void writeInvoices(List<InvoiceHistoryItemResponse> invoices) {
        try {
            ensureStorageExists();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(storagePath.toFile(), invoices);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write invoice history file", e);
        }
    }

    private void ensureStorageExists() throws IOException {
        Files.createDirectories(storagePath.getParent());
        if (!Files.exists(storagePath)) {
            Files.writeString(storagePath, "[]");
        }
    }
}