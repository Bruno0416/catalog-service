package com.mariluz.catalog.service;

import com.mariluz.catalog.dto.*;

public interface CatalogService {
    // 1. crear producto
    public ProductResponse createProduct(CreateProductRequest request);
    // 2. actualizar producto
    public ProductResponse updateProduct(UpdateProductRequest request);
    // 3. obtener producto por id
    public ProductResponse getProductById(Integer id);
    // 4. listar todos los productos
    public GetProductsResponse getAllProducts();
    // 5. obtener lista de productos por id List<Integer> ids
    public GetProductsResponse getProductsByIds(ProductsByIdRequest request);
    // 6. actualizar stock
    public void updateStock(UpdateStockRequest request);
}
