package com.blockchaincafe.blockchain.repository;

import com.blockchaincafe.blockchain.domain.model.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<BlockEntity, String> {
    Optional<BlockEntity> findTopByOrderByBlockIndexDesc();
    List<BlockEntity> findAllByOrderByBlockIndexAsc();
}
