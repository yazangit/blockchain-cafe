package com.blockchaincafe.wallet.domain.model;

import com.blockchaincafe.wallet.domain.enums.WalletType;
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

@Entity
@Table(name = "wallets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletEntity {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private WalletType type;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;
}
