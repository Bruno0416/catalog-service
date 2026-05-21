package com.mariluz.catalog.controller;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.GetProductsResponse;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.ProductsByIdRequest;
import com.mariluz.catalog.dto.UpdateProductRequest;
import com.mariluz.catalog.dto.UpdateStockRequest;
import com.mariluz.catalog.service.CatalogService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class CatalogController {

    private final CatalogService service;

    // 1. Crear producto
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
        @Valid @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            service.createProduct(request)
        );
    }

    // 2. Actualizar producto
    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(
        @Valid @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.updateProduct(request)
        );
    }

    // 3. Obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
        @PathVariable @Min(
            value = 1,
            message = "El id debe ser mayor que 0"
        ) Integer id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.getProductById(id)
        );
    }

    // 4. Listar todos los productos
    @GetMapping("/products")
    public ResponseEntity<GetProductsResponse> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.getAllProducts()
        );
    }

    // ─── Métodos para comunicarse con sales-service ───────────────────────────

    // 5. Obtener lista de productos por ids
    @PostMapping("/products/ids")
    public ResponseEntity<GetProductsResponse> getProductsByIds(
        @Valid @RequestBody ProductsByIdRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.getProductsByIds(request)
        );
    }

    // 6. Actualizar stock
    @PutMapping("/update-stock")
    public ResponseEntity<Void> updateStock(
        @Valid @RequestBody UpdateStockRequest request
    ) {
        service.updateStock(request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
