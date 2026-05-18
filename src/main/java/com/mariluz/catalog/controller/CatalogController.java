package com.mariluz.catalog.controller;

import com.mariluz.catalog.dto.*;
import com.mariluz.catalog.service.CatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService service;

    // 1. crear producto
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
        @Valid @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            service.createProduct(request)
        );
    }

    // 2. actualizar producto
    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(
        @Valid @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.updateProduct(request)
        );
    }

    // 3. obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
        @PathVariable Integer id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.getProductById(id)
        );
    }

    // 4. listar todos los productos
    @GetMapping("/products")
    public ResponseEntity<GetProductsResponse> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.getAllProducts()
        );
    }

    // 5. obtener lista de productos por id List<Integer> ids
    @PostMapping("/products/ids")
    public ResponseEntity<GetProductsResponse> getProductsByIds(
        @Valid @RequestBody ProductsByIdRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.getProductsByIds(request)
        );
    }

    // 6. actualizar stock
    @PutMapping("/update-stock")
    public ResponseEntity<Void> updateStock(
        @Valid @RequestBody UpdateStockRequest request
    ) {
        service.updateStock(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
