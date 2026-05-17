package com.mariluz.catalog.controller;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.UpdateProductRequest;
import com.mariluz.catalog.service.CatalogService;
import jakarta.validation.Valid;
import java.util.List;
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
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.getAllProducts()
        );
    }
}
