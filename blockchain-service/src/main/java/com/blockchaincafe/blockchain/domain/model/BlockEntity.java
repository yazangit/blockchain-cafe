package com.blockchaincafe.blockchain.domain.model;

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
@Table(name = "blocks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockEntity {

    @Id
    private String id;

    @Column(name = "block_index", nullable = false, unique = true)
    private Integer blockIndex;

    @Column(name = "order_id", nullable = false, length = 64)
    private String orderId;

    @Column(name = "payment_id", nullable = false, length = 128)
    private String paymentId;

    @Column(name = "gross_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal grossTotal;

    @Column(name = "net_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal netTotal;

    @Column(name = "total_vat", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalVat;

    @Column(name = "payload", nullable = false, length = 4000)
    private String payload;

    @Column(name = "previous_hash", nullable = false, length = 128)
    private String previousHash;

    @Column(name = "hash_value", nullable = false, length = 128)
    private String hashValue;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
