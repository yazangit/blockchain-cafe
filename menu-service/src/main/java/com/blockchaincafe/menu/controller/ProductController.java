package com.blockchaincafe.menu.controller;

import com.blockchaincafe.menu.dto.request.CreateProductRequest;
import com.blockchaincafe.menu.dto.request.UpdateProductRequest;
import com.blockchaincafe.menu.dto.response.ProductResponse;
import com.blockchaincafe.menu.service.ProductService;
import com.blockchaincafe.shared.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/admin/products")
    public SuccessResponse<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        return SuccessResponse.<ProductResponse>builder()
                .success(true)
                .data(productService.create(request))
                .build();
    }

    @PutMapping("/api/admin/products/{productId}")
    public SuccessResponse<ProductResponse> update(
            @PathVariable("productId") String productId,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        return SuccessResponse.<ProductResponse>builder()
                .success(true)
                .data(productService.update(productId, request))
                .build();
    }

    @GetMapping("/{productId}")
    public SuccessResponse<ProductResponse> getById(@PathVariable("productId") String productId) {
        return SuccessResponse.<ProductResponse>builder()
                .success(true)
                .data(productService.getById(productId))
                .build();
    }

   @GetMapping
    public SuccessResponse<List<ProductResponse>> getAll(
            @RequestParam(name = "activeOnly", defaultValue = "false") boolean activeOnly
    ) {
        return SuccessResponse.<List<ProductResponse>>builder()
                .success(true)
                .data(activeOnly ? productService.getAllActive() : productService.getAll())
                .build();
    }

    @DeleteMapping("/api/admin/products/{productId}")
    public SuccessResponse<Void> delete(@PathVariable("productId") String productId) {
        productService.delete(productId);
        return SuccessResponse.<Void>builder()
                .success(true)
                .data(null)
                .build();
    }
}
