package com.mariluz.catalog.controller;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.model.Product;
import com.mariluz.catalog.service.CatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService service;

    // 1. crear producto
    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(
        @Valid @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            service.createProduct(request)
        );
    }
    // 2. actualizar producto
    // 3. obtener producto por id
    // 4. listar todos los productos
}
