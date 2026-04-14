package com.blockchaincafe.vat.repository;

import com.blockchaincafe.vat.domain.model.VatRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VatRecordRepository extends JpaRepository<VatRecordEntity, String> {
    Optional<VatRecordEntity> findByOrderId(String orderId);
}
