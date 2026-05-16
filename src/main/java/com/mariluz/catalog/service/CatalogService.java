package com.mariluz.catalog.service;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.UpdateProductRequest;

public interface CatalogService {
    // 1. crear producto
    public ProductResponse createProduct(CreateProductRequest request);
    // 2. actualizar producto
    public ProductResponse updateProduct(UpdateProductRequest request);
    // 3. obtener producto por id
    // 4. listar todos los productos
}
