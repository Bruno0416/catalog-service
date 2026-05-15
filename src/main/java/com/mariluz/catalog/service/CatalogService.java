package com.mariluz.catalog.service;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.model.Product;

public interface CatalogService {
    // 1. crear producto
    public Product createProduct(CreateProductRequest request);
    // 2. actualizar producto
    // 3. obtener producto por id
    // 4. listar todos los productos
}
