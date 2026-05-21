package com.mariluz.catalog.service;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.GetProductsResponse;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.ProductsByIdRequest;
import com.mariluz.catalog.dto.UpdateProductRequest;
import com.mariluz.catalog.dto.UpdateStockRequest;

public interface CatalogService {
    // 1. Crear producto
    ProductResponse createProduct(CreateProductRequest request);

    // 2. Actualizar producto
    ProductResponse updateProduct(UpdateProductRequest request);

    // 3. Obtener producto por id
    ProductResponse getProductById(Integer id);

    // 4. Listar todos los productos
    GetProductsResponse getAllProducts();

    // 5. Obtener lista de productos por ids
    GetProductsResponse getProductsByIds(ProductsByIdRequest request);

    // 6. Actualizar stock
    void updateStock(UpdateStockRequest request);
}
