package com.mariluz.catalog.service;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.GetProductsResponse;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.ProductsByIdRequest;
import com.mariluz.catalog.dto.RestoreStockRequest;
import com.mariluz.catalog.dto.UpdateProductRequest;
import com.mariluz.catalog.dto.UpdateStockRequest;
import com.mariluz.catalog.exceptions.ForbiddenOperationException;
import com.mariluz.catalog.exceptions.InsufficientStockException;
import com.mariluz.catalog.exceptions.ProductDoesNotExistException;
import com.mariluz.catalog.mapper.ProductMapper;
import com.mariluz.catalog.model.Product;
import com.mariluz.catalog.model.User;
import com.mariluz.catalog.repository.CatalogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository repo;
    private final ProductMapper mapper;

    // ─── Helpers privados para validar rol de usuario ────────────────────────

    private User getCurrentUser() {
        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            // ForbiddenOperationException reemplaza a UnauthorizedOperationException
            throw new ForbiddenOperationException(
                "No hay un usuario autenticado"
            );
        }
        return user;
    }

    private void validateAdminAccess(String message) {
        User user = getCurrentUser();
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new ForbiddenOperationException(message);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        // 1. Validar que la solicitud la manda un admin
        validateAdminAccess("Solo los administradores pueden crear productos");

        // 2. Crear tupla
        Product p = repo.save(
            Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build()
        );
        // 3. Retornar ProductResponse con los datos de la tupla creada
        return mapper.toResponse(p);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(UpdateProductRequest request) {
        // 1. Validar que la solicitud la manda un admin
        validateAdminAccess(
            "Solo los administradores pueden actualizar productos"
        );

        // 2. Validar si existe el producto usando id
        Product product = repo
            .findById(request.getId())
            .orElseThrow(ProductDoesNotExistException::new);

        // 3. Actualizar el producto (no es necesario el .save, @Transactional lo actualiza)
        mapper.updateProduct(request, product);

        // 4. Retornar el producto actualizado
        return mapper.toResponse(product);
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        // 1. Buscar producto | si no existe lanzamos excepción
        Product p = repo
            .findById(id)
            .orElseThrow(ProductDoesNotExistException::new);

        // 2. Retornar el producto encontrado
        return mapper.toResponse(p);
    }

    @Override
    public GetProductsResponse getAllProducts() {
        // 1. Obtener todos los productos
        List<Product> products = repo.findAll();

        // 2. Convertir la lista en ProductResponse y retornarla
        return GetProductsResponse.builder()
            .products(products.stream().map(mapper::toResponse).toList())
            .build();
    }

    @Override
    public GetProductsResponse getProductsByIds(ProductsByIdRequest request) {
        // 1. Buscar productos usando la lista de IDs
        List<Integer> requestedIds = request.getIds();
        List<Product> products = repo.findAllById(requestedIds);

        // 2. Verificar que todos los IDs solicitados fueron encontrados
        if (products.size() != requestedIds.size()) {
            List<Integer> foundIds = products
                .stream()
                .map(Product::getId)
                .toList();
            List<Integer> missingIds = requestedIds
                .stream()
                .filter(id -> !foundIds.contains(id))
                .toList();
            throw new ProductDoesNotExistException(
                "No se encontraron los productos con los siguientes IDs: " +
                    missingIds
            );
        }

        // 3. Convertir la lista en ProductResponse y retornarla
        return GetProductsResponse.builder()
            .products(products.stream().map(mapper::toResponse).toList())
            .build();
    }

    @Override
    @Transactional
    public void updateStock(UpdateStockRequest request) {
        // 1. Verificar que el producto existe
        Product p = repo
            .findById(request.getId())
            .orElseThrow(ProductDoesNotExistException::new);

        // 2. Validar que el stock no quede negativo
        int newQuantity = p.getQuantity() - request.getQuantity();
        if (newQuantity < 0) {
            throw new InsufficientStockException(
                "Stock insuficiente para el producto con ID " +
                    request.getId() +
                    ". Disponible: " +
                    p.getQuantity() +
                    ", solicitado: " +
                    request.getQuantity()
            );
        }

        // 3. Actualizar y persistir el nuevo stock
        p.setQuantity(newQuantity);
        repo.save(p);
    }

    // 7. restore stock (en caso de un error la transaccion createSale, se revierte el stock)
    @Override
    @Transactional
    public void restoreStock(RestoreStockRequest request) {
        // 1. validar producto
        Product p = repo
            .findById(request.getId())
            .orElseThrow(ProductDoesNotExistException::new);

        // 2. actualizar stock (pQuantity + requestQuantity = restoredQuantity)
        p.setQuantity(p.getQuantity() + request.getQuantity());
        repo.save(p);

        System.out.println("[DEBUG] stock restaurado correctamente");
    }
}
