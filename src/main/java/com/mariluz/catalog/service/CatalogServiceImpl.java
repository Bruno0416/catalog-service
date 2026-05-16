package com.mariluz.catalog.service;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.UpdateProductRequest;
import com.mariluz.catalog.exceptions.ProductDoesNotExistException;
import com.mariluz.catalog.exceptions.UnauthorizedOperationException;
import com.mariluz.catalog.model.Product;
import com.mariluz.catalog.model.User;
import com.mariluz.catalog.repository.CatalogRepository;
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
            .id(request.getId())
            .name(p.getName())
            .price(p.getPrice())
            .quantity(p.getQuantity())
            .build();
    }
}
