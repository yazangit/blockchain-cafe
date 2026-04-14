package com.blockchaincafe.menu.service.impl;

import com.blockchaincafe.menu.domain.model.ProductEntity;
import com.blockchaincafe.menu.dto.request.CreateProductRequest;
import com.blockchaincafe.menu.dto.request.UpdateProductRequest;
import com.blockchaincafe.menu.dto.response.ProductResponse;
import com.blockchaincafe.menu.exception.ProductNotFoundException;
import com.blockchaincafe.menu.mapper.ProductMapper;
import com.blockchaincafe.menu.repository.ProductRepository;
import com.blockchaincafe.menu.service.ProductService;
import com.blockchaincafe.shared.util.MoneyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(CreateProductRequest request) {
        ProductEntity entity = ProductEntity.builder()
                .id(request.getId())
                .name(request.getName())
                .priceGross(MoneyUtils.normalize(request.getPriceGross()))
                .vatRate(request.getVatRate())
                .active(request.getActive())
                .createdAt(Instant.now())
                .build();

        return ProductMapper.toResponse(productRepository.save(entity));
    }

    @Override
    public ProductResponse update(String productId, UpdateProductRequest request) {
        ProductEntity entity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

        entity.setName(request.getName());
        entity.setPriceGross(MoneyUtils.normalize(request.getPriceGross()));
        entity.setVatRate(request.getVatRate());
        entity.setActive(request.getActive());

        return ProductMapper.toResponse(productRepository.save(entity));
    }

    @Override
    public ProductResponse getById(String productId) {
        return productRepository.findById(productId)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getAllActive() {
        return productRepository.findAllByActiveTrue().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found: " + productId);
        }
        productRepository.deleteById(productId);
    }
}
