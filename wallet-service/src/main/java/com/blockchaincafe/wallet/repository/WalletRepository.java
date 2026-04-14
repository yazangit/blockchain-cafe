package com.blockchaincafe.wallet.repository;

import com.blockchaincafe.wallet.domain.enums.WalletType;
import com.blockchaincafe.wallet.domain.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, String> {
    Optional<WalletEntity> findByType(WalletType type);
}
