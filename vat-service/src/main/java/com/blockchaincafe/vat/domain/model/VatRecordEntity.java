package com.blockchaincafe.vat.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "vat_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VatRecordEntity {

    @Id
    private String id;

    @Column(name = "order_id", nullable = false, unique = true, length = 64)
    private String orderId;

    @Column(name = "payer_type", nullable = false, length = 32)
    private String payerType;

    @Column(name = "invoice_number", nullable = false, length = 128)
    private String invoiceNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "vat_id")
    private String vatId;

    @Column(name = "gross_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal grossTotal;

    @Column(name = "net_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal netTotal;

    @Column(name = "total_vat", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalVat;

    @Column(name = "vat_19", nullable = false, precision = 15, scale = 2)
    private BigDecimal vat19;

    @Column(name = "vat_7", nullable = false, precision = 15, scale = 2)
    private BigDecimal vat7;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
