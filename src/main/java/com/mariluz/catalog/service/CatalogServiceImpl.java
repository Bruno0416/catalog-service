package com.mariluz.catalog.service;

import com.mariluz.catalog.dto.*;
import com.mariluz.catalog.exceptions.ProductDoesNotExistException;
import com.mariluz.catalog.exceptions.UnauthorizedOperationException;
import com.mariluz.catalog.model.Product;
import com.mariluz.catalog.model.User;
import com.mariluz.catalog.repository.CatalogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository repo;

    // ------------------ Helpers privados para validar rol usuario -------------------

    private User getCurrentUser() {
        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new UnauthorizedOperationException(
                "No hay un usuario autenticado"
            );
        }

        return user;
    }

    private void validateAdminAccess(String message) {
        User user = getCurrentUser();

        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            // si el usuario no es admin arrojamos un error
            throw new UnauthorizedOperationException(message);
        }
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        // 1. validar que la solicitud la manda un admin
        validateAdminAccess("Solo los administradores pueden crear productos");

        // 2. crear tupla
        Product p = repo.save(
            Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build()
        );
        // retornar ProductResponse con los datos de la tupla creada
        return ProductResponse.builder()
            .id(p.getId())
            .name(p.getName())
            .price(p.getPrice())
            .quantity(p.getQuantity())
            .build();
    }

    @Override
    public ProductResponse updateProduct(UpdateProductRequest request) {
        // 1. validar que la solicitud la manda un admin
        validateAdminAccess(
            "Solo los administradores pueden actualizar productos"
        );
        // 2. validar si existe el producto usando id
        if (!repo.existsById(request.getId())) {
            throw new ProductDoesNotExistException();
        }
        // 3. actualizar el producto
        Product p = repo.save(
            Product.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build()
        );
        // 4. retornar el producto actualizado
        return ProductResponse.builder()
            .id(p.getId())
            .name(p.getName())
            .price(p.getPrice())
            .quantity(p.getQuantity())
            .build();
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        // 1. buscar producto | si no existe arrojamos una excepcion
        Product p = repo
            .findById(id)
            .orElseThrow(() -> new ProductDoesNotExistException());

        // 2. retornar el producto encontrado
        return ProductResponse.builder()
            .id(p.getId())
            .name(p.getName())
            .price(p.getPrice())
            .quantity(p.getQuantity())
            .build();
    }

    @Override
    public GetProductsResponse getAllProducts() {
        // 1. obtener todos los productos
        List<Product> products = repo.findAll();
        // 2. convertir la lista de productos en una lista de ProductResponse y retornarla
        return GetProductsResponse.builder()
            .products(
                products
                    // Convierte la coleccion List<Product> en un flujo de datos (stream) que permite procesar los elementos de manera secuencial
                    .stream()
                    // toma los elementos del stream ('p') y los mapea a ProductResponse
                    .map(p ->
                        ProductResponse.builder()
                            .id(p.getId())
                            .name(p.getName())
                            .price(p.getPrice())
                            .quantity(p.getQuantity())
                            .build()
                    )
                    // el .toList() recolecta los nuevos objetos y transformados y los arregla como una lista
                    .toList()
            )
            .build();
    }

    @Override
    public GetProductsResponse getProductsByIds(ProductsByIdRequest request) {
        /*
        Tiene la misma logica de getAllProducts pero en vez de usar findAll()
        usamos -> findAllById(ids) para encontrar solo la lista de ids/productos deseada
        */

        // 1. buscar productos usando la lista de IDs
        List<Product> products = repo.findAllById(request.getIds());
        // 3. convertir la lista de productos en una lista de ProductResponse y retornarla
        return GetProductsResponse.builder()
            .products(
                products
                    .stream()
                    .map(p ->
                        ProductResponse.builder()
                            .id(p.getId())
                            .name(p.getName())
                            .price(p.getPrice())
                            .quantity(p.getQuantity())
                            .build()
                    )
                    .toList()
            )
            .build();
    }

    @Override
    public void updateStock(UpdateStockRequest request) {
        /*
        1. verificar que el producto existe.
        Nos aseguramos de que el producto existe y lo guardamos en una variable
            y si no, arrojamos inmediatamente una excepcion para controlar el error.
        */
        Product p = repo
            .findById(request.getId())
            .orElseThrow(() -> new ProductDoesNotExistException());
        // 2. actualizar producto
        p.setQuantity(p.getQuantity() - request.getQuantity()); // actualizamos el stock obteniendo el actual y restandole el vendido
        repo.save(p); // guardamos la entidad seteada con el nuevo valor
    }
}
