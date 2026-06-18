package com.mariluz.catalog.controller;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.ErrorResponse;
import com.mariluz.catalog.dto.GetProductsResponse;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.ProductsByIdRequest;
import com.mariluz.catalog.dto.RestoreStockRequest;
import com.mariluz.catalog.dto.UpdateProductRequest;
import com.mariluz.catalog.dto.UpdateStockRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface CatalogApi {
    // 1. Crear producto
    @Operation(
        summary = "Crear producto",
        description = "Registra un nuevo producto en el sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Producto registrado exitosamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "id": 1,
                      "name": "Producto de ejemplo",
                      "description": "Descripción del producto",
                      "price": 100.0,
                      "stock": 50
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/create",
                        "errors": null,
                        "message": "Error de validacion",
                        "status": 400,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/create",
                        "errors": null,
                        "message": "Error interno del servidor.",
                        "status": 500,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/create",
                        "errors": {
                            "error": "No hay un usuario autenticado."
                        },
                        "message": "Usuario no autenticado",
                        "status": 401,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Operación no autorizada.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/create",
                        "errors": {
                            "error": "Solo los administradores pueden crear productos"
                        },
                        "message": "Debe ser administrador para realizar esta operacion",
                        "status": 403,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
    })
    ResponseEntity<ProductResponse> createProduct(CreateProductRequest request);

    // 2. Actualizar producto
    @Operation(
        summary = "Actualizar producto",
        description = "Actualiza un producto existente en el sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado exitosamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "id": 1,
                      "name": "Producto de ejemplo",
                      "description": "Descripción del producto",
                      "price": 100.0,
                      "stock": 50
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update",
                        "errors": null,
                        "message": "Error de validacion",
                        "status": 400,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update",
                        "errors": null,
                        "message": "Error interno del servidor.",
                        "status": 500,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update",
                        "errors": {
                            "error": "No hay un usuario autenticado."
                        },
                        "message": "Usuario no autenticado",
                        "status": 401,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Operación no autorizada.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update",
                        "errors": {
                            "error": "Solo los administradores pueden actualizar productos"
                        },
                        "message": "Debe ser administrador para realizar esta operacion",
                        "status": 403,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update",
                        "errors": {
                            "error": "El producto no existe"
                        },
                        "message": "El producto no existe",
                        "status": 404,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
    })
    ResponseEntity<ProductResponse> updateProduct(UpdateProductRequest request);

    // 3. Obtener producto por id
    @Operation(
        summary = "Obtener producto por id",
        description = "Obtiene un producto existente en el sistema por su id."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto obtenido exitosamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "id": 1,
                      "name": "Producto de ejemplo",
                      "description": "Descripción del producto",
                      "price": 100.0,
                      "stock": 50
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/1",
                        "errors": null,
                        "message": "Error de validacion",
                        "status": 400,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update",
                        "errors": null,
                        "message": "Error interno del servidor.",
                        "status": 500,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/1",
                        "errors": {
                            "error": "No existe un producto con ese id"
                        },
                        "message": "El producto no existe",
                        "status": 404,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
    })
    ResponseEntity<ProductResponse> getProductById(Integer id);

    // 4. Listar todos los productos
    @Operation(
        summary = "Listar todos los productos",
        description = "Obtiene la lista de todos los productos en el sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida exitosamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = GetProductsResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "products": [
                        {
                          "id": 1,
                          "name": "Producto de ejemplo",
                          "description": "Descripción del producto",
                          "price": 100.0,
                          "stock": 50
                        }
                      ]
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/products",
                        "errors": null,
                        "message": "Error interno del servidor.",
                        "status": 500,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
    })
    ResponseEntity<GetProductsResponse> getAllProducts();

    // ─── Métodos para comunicarse con sales-service ───────────────────────────

    // 5. Obtener lista de productos por ids
    @Operation(
        summary = "Obtener lista de productos por ids",
        description = "Obtiene una lista de productos en el sistema dados sus ids."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida exitosamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = GetProductsResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "products": [
                        {
                          "id": 1,
                          "name": "Producto de ejemplo",
                          "description": "Descripción del producto",
                          "price": 100.0,
                          "stock": 50
                        }
                      ]
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/products/ids",
                        "errors": {
                            "ids": "must not be null"
                        },
                        "message": "Error de validación",
                        "status": 400,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/products/ids",
                        "errors": {
                            "error": "No se encontraron los productos con los siguientes IDs: [2]"
                        },
                        "message": "No se encontraron los productos con los siguientes IDs: [2]",
                        "status": 404,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/products/ids",
                        "errors": null,
                        "message": "Error interno del servidor.",
                        "status": 500,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
    })
    ResponseEntity<GetProductsResponse> getProductsByIds(
        ProductsByIdRequest request
    );

    // 6. Actualizar stock
    @Operation(
        summary = "Actualizar stock",
        description = "Actualiza el stock de un producto existente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Stock actualizado exitosamente."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update-stock",
                        "errors": {
                            "quantity": "must be greater than 0"
                        },
                        "message": "Error de validación",
                        "status": 400,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update-stock",
                        "errors": {
                            "error": "El producto no existe"
                        },
                        "message": "El producto no existe",
                        "status": 404,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "422",
            description = "Stock insuficiente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update-stock",
                        "errors": {
                            "error": "Stock insuficiente para el producto con ID 1. Disponible: 5, solicitado: 10"
                        },
                        "message": "Stock insuficiente para el producto con ID 1. Disponible: 5, solicitado: 10",
                        "status": 422,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/update-stock",
                        "errors": null,
                        "message": "Error interno del servidor.",
                        "status": 500,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
    })
    ResponseEntity<Void> updateStock(UpdateStockRequest request);

    // 7. restore stock (en caso de que de un error la transaccion createSale, se revierte el stock)
    @Operation(
        summary = "Restaurar stock",
        description = "Restaura el stock de un producto existente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Stock restaurado exitosamente."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/restore-stock",
                        "errors": {
                            "quantity": "must be greater than 0"
                        },
                        "message": "Error de validación",
                        "status": 400,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/restore-stock",
                        "errors": {
                            "error": "El producto no existe"
                        },
                        "message": "No existe un producto con ese id",
                        "status": 404,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "endpoint": "/catalog/restore-stock",
                        "errors": null,
                        "message": "Error interno del servidor.",
                        "status": 500,
                        "timeStamp": "2026-06-12T05:11:58"
                    }
                    """
                )
            )
        ),
    })
    ResponseEntity<Void> restoreStock(RestoreStockRequest request);
}
