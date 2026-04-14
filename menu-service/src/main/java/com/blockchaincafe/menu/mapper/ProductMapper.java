package com.blockchaincafe.menu.mapper;

import com.blockchaincafe.menu.domain.model.ProductEntity;
import com.blockchaincafe.menu.dto.response.ProductResponse;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(ProductEntity entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .priceGross(entity.getPriceGross())
                .vatRate(entity.getVatRate())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
