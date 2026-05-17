package com.mariluz.catalog.service;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.UpdateProductRequest;
import java.util.List;

public interface CatalogService {
    // 1. crear producto
    public ProductResponse createProduct(CreateProductRequest request);
    // 2. actualizar producto
    public ProductResponse updateProduct(UpdateProductRequest request);
    // 3. obtener producto por id
    public ProductResponse getProductById(Integer id);
    // 4. listar todos los productos
    public List<ProductResponse> getAllProducts();
    // 5. obtener lista de productos por id List<Integer> ids
    public List<ProductResponse> getProductsByIds(List<Integer> ids);
    // 6. actualizar stock
    public void updateStock(Integer id, Integer quantitySold);
}
