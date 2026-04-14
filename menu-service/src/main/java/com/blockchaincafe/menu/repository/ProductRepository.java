package com.blockchaincafe.menu.repository;

import com.blockchaincafe.menu.domain.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findAllByActiveTrue();
}
