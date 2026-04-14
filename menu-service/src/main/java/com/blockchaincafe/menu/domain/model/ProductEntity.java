package com.blockchaincafe.menu.domain.model;

import com.blockchaincafe.shared.enums.VatRateType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "price_gross", nullable = false, precision = 15, scale = 2)
    private BigDecimal priceGross;

    @Enumerated(EnumType.STRING)
    @Column(name = "vat_rate", nullable = false)
    private VatRateType vatRate;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
