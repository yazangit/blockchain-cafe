package com.blockchaincafe.menu.service;

import com.blockchaincafe.menu.dto.request.CreateProductRequest;
import com.blockchaincafe.menu.dto.request.UpdateProductRequest;
import com.blockchaincafe.menu.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(CreateProductRequest request);
    ProductResponse update(String productId, UpdateProductRequest request);
    ProductResponse getById(String productId);
    List<ProductResponse> getAll();
    List<ProductResponse> getAllActive();
    void delete(String productId);
}
