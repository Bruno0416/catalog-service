package com.mariluz.catalog.controller;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.GetProductsResponse;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.ProductsByIdRequest;
import com.mariluz.catalog.dto.RestoreStockRequest;
import com.mariluz.catalog.dto.UpdateProductRequest;
import com.mariluz.catalog.dto.UpdateStockRequest;
import com.mariluz.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
@Validated
public class CatalogController implements CatalogApi {

    private final CatalogService service;

    // 1. Crear producto
    @Override
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
        @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            service.createProduct(request)
        );
    }

    // 2. Actualizar producto
    @Override
    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(
        @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.ok(service.updateProduct(request));
    }

    // 3. Obtener producto por id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(Integer id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    // 4. Listar todos los productos
    @Override
    @GetMapping("/products")
    public ResponseEntity<GetProductsResponse> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    // ─── Métodos para comunicarse con sales-service ───────────────────────────

    // 5. Obtener lista de productos por ids
    @Override
    @PostMapping("/products/ids")
    public ResponseEntity<GetProductsResponse> getProductsByIds(
        @RequestBody ProductsByIdRequest request
    ) {
        return ResponseEntity.ok(service.getProductsByIds(request));
    }

    // 6. Actualizar stock
    @Override
    @PutMapping("/update-stock")
    public ResponseEntity<Void> updateStock(
        @RequestBody UpdateStockRequest request
    ) {
        service.updateStock(request);

        return ResponseEntity.noContent().build();
    }

    // 7. restore stock (en caso de que de un error la transaccion createSale, se revierte el stock)
    @Override
    @PutMapping("/restore-stock")
    public ResponseEntity<Void> restoreStock(
        @RequestBody RestoreStockRequest request
    ) {
        service.restoreStock(request);

        return ResponseEntity.noContent().build();
    }
}
