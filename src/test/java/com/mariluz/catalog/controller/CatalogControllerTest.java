package com.mariluz.catalog.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mariluz.catalog.dto.CreateProductRequest;
import com.mariluz.catalog.dto.GetProductsResponse;
import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.ProductsByIdRequest;
import com.mariluz.catalog.dto.RestoreStockRequest;
import com.mariluz.catalog.dto.UpdateProductRequest;
import com.mariluz.catalog.dto.UpdateStockRequest;
import com.mariluz.catalog.exceptions.InsufficientStockException;
import com.mariluz.catalog.exceptions.ProductDoesNotExistException;
import com.mariluz.catalog.security.JwtUtil;
import com.mariluz.catalog.service.CatalogService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

@WebMvcTest(CatalogController.class)
@AutoConfigureMockMvc(addFilters = false) // desactiva filtro JWT y seguridad para ejecutar el test
public class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper objectMapper; // para mapear objetos/clases a json

    @MockitoBean
    private CatalogService service;

    @MockitoBean
    private JwtUtil jwtUtil; // importante para que funcione el service

    // -------------- CREATE PRODUCT --------------

    // Codigo 201
    @Test
    public void testCreateProduct() throws Exception {
        // 1. preparar request prueba
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Producto de prueba");
        request.setPrice(100);
        request.setQuantity(10);

        // 2. preparar respuesta
        ProductResponse response = ProductResponse.builder()
            .id(1)
            .name("Producto de prueba")
            .price(100)
            .quantity(10)
            .build();

        // 3. ejecutar test
        when(service.createProduct(request)).thenReturn(response);

        mockMvc
            .perform(
                post("/catalog/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isCreated());
    }

    // Codigo 400 - campos invalidos (nombre vacio, precio y stock negativos)
    @Test
    public void testCreateProductInvalidFields() throws Exception {
        // 1. preparar request prueba
        CreateProductRequest request = new CreateProductRequest();
        request.setName("");
        request.setPrice(-100);
        request.setQuantity(-10);

        // 2. ejecutar test
        mockMvc
            .perform(
                post("/catalog/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest());
    }

    // -------------- UPDATE PRODUCT --------------

    // Codigo 200
    @Test
    public void testUpdateProduct() throws Exception {
        // 1. preparar request prueba
        UpdateProductRequest request = new UpdateProductRequest();
        request.setId(1);
        request.setName("Producto actualizado");
        request.setPrice(150);
        request.setQuantity(20);

        // 2. preparar respuesta
        ProductResponse response = ProductResponse.builder()
            .id(1)
            .name("Producto actualizado")
            .price(150)
            .quantity(20)
            .build();

        // 3. ejecutar test
        when(service.updateProduct(request)).thenReturn(response);

        mockMvc
            .perform(
                put("/catalog/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk());
    }

    // Codigo 400 - campos invalidos (id negativo, nombre vacio, precio y cantidad negativos)
    @Test
    public void testUpdateProductInvalidFields() throws Exception {
        // 1. preparar request prueba
        UpdateProductRequest request = new UpdateProductRequest();
        request.setId(-1);
        request.setName("");
        request.setPrice(-100);
        request.setQuantity(-10);

        // 2. ejecutar test
        mockMvc
            .perform(
                put("/catalog/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest());
    }

    // Codigo 404 - producto no existe
    @Test
    public void testUpdateProductNotFound() throws Exception {
        // 1. preparar request prueba
        UpdateProductRequest request = new UpdateProductRequest();
        request.setId(999);
        request.setName("Producto no existente");
        request.setPrice(100);
        request.setQuantity(10);

        // 2. ejecutar test
        when(service.updateProduct(request)).thenThrow(
            new ProductDoesNotExistException("No existe un producto con ese id")
        );

        mockMvc
            .perform(
                put("/catalog/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isNotFound());
    }

    // -------------- GET PRODUCT BY ID --------------

    // Codigo 200
    @Test
    public void testGetProductById() throws Exception {
        // 1. preparar respuesta
        ProductResponse response = ProductResponse.builder()
            .id(1)
            .name("Producto de prueba")
            .price(100)
            .quantity(10)
            .build();

        // 2. ejecutar test
        when(service.getProductById(1)).thenReturn(response);

        mockMvc.perform(get("/catalog/1")).andExpect(status().isOk());
    }

    // Codigo 400 - id invalido (debe ser mayor que 0)
    @Test
    public void testGetProductByIdInvalid() throws Exception {
        // 1. ejecutar test con id = 0
        mockMvc.perform(get("/catalog/0")).andExpect(status().isBadRequest());
    }

    // Codigo 404 - producto no existe
    @Test
    public void testGetProductByIdNotFound() throws Exception {
        // 1. ejecutar test
        when(service.getProductById(999)).thenThrow(
            new ProductDoesNotExistException()
        );

        mockMvc.perform(get("/catalog/999")).andExpect(status().isNotFound());
    }

    // -------------- GET ALL PRODUCTS --------------

    // Codigo 200
    @Test
    public void testGetAllProducts() throws Exception {
        // 1. preparar respuesta
        GetProductsResponse response = GetProductsResponse.builder()
            .products(List.of())
            .build();

        // 2. ejecutar test
        when(service.getAllProducts()).thenReturn(response);

        mockMvc.perform(get("/catalog/products")).andExpect(status().isOk());
    }

    // -------------- GET PRODUCTS BY IDS --------------

    // Codigo 200
    @Test
    public void testGetProductsByIds() throws Exception {
        // 1. preparar request prueba
        ProductsByIdRequest request = new ProductsByIdRequest();
        request.setIds(List.of(1, 2, 3));

        // 2. preparar respuesta
        GetProductsResponse response = GetProductsResponse.builder()
            .products(List.of())
            .build();

        // 3. ejecutar test
        when(service.getProductsByIds(request)).thenReturn(response);

        mockMvc
            .perform(
                post("/catalog/products/ids")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk());
    }

    // Codigo 400 - lista vacia
    @Test
    public void testGetProductsByIdsEmptyList() throws Exception {
        // 1. preparar request prueba con lista vacia
        ProductsByIdRequest request = new ProductsByIdRequest();
        request.setIds(List.of());

        // 2. ejecutar test
        mockMvc
            .perform(
                post("/catalog/products/ids")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest());
    }

    // Codigo 400 - ID negativo en lista
    @Test
    public void testGetProductsByIdsNegativeId() throws Exception {
        // 1. preparar request prueba con ID invalido en lista
        ProductsByIdRequest request = new ProductsByIdRequest();
        request.setIds(List.of(-1, 2, 3));

        // 2. ejecutar test
        mockMvc
            .perform(
                post("/catalog/products/ids")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest());
    }

    // -------------- UPDATE STOCK --------------

    // Codigo 204
    @Test
    public void testUpdateStock() throws Exception {
        // 1. preparar request prueba
        UpdateStockRequest request = new UpdateStockRequest();
        request.setId(1);
        request.setQuantity(5);

        // 2. ejecutar test
        mockMvc
            .perform(
                put("/catalog/update-stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isNoContent());
    }

    // Codigo 400 - campos invalidos (id y cantidad negativos)
    @Test
    public void testUpdateStockInvalidFields() throws Exception {
        // 1. preparar request prueba
        UpdateStockRequest request = new UpdateStockRequest();
        request.setId(-1);
        request.setQuantity(-5);

        // 2. ejecutar test
        mockMvc
            .perform(
                put("/catalog/update-stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest());
    }

    // Codigo 400 - JSON malformado
    @Test
    public void testUpdateStockMalformedJson() throws Exception {
        // 1. ejecutar test con body no valido
        mockMvc
            .perform(
                put("/catalog/update-stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ invalid json }")
            )
            .andExpect(status().isBadRequest());
    }

    // Codigo 404 - producto no existe
    @Test
    public void testUpdateStockProductNotFound() throws Exception {
        // 1. preparar request prueba
        UpdateStockRequest request = new UpdateStockRequest();
        request.setId(999);
        request.setQuantity(5);

        // 2. ejecutar test
        doThrow(new ProductDoesNotExistException())
            .when(service)
            .updateStock(request);

        mockMvc
            .perform(
                put("/catalog/update-stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isNotFound());
    }

    // Codigo 422 - stock insuficiente
    @Test
    public void testUpdateStockInsufficientStock() throws Exception {
        // 1. preparar request prueba
        UpdateStockRequest request = new UpdateStockRequest();
        request.setId(1);
        request.setQuantity(9999);

        // 2. ejecutar test
        doThrow(
            new InsufficientStockException(
                "Stock insuficiente para el producto con id 1"
            )
        )
            .when(service)
            .updateStock(request);

        mockMvc
            .perform(
                put("/catalog/update-stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isUnprocessableContent());
    }

    // -------------- RESTORE STOCK --------------

    // Codigo 204
    @Test
    public void testRestoreStock() throws Exception {
        // 1. preparar request prueba
        RestoreStockRequest request = new RestoreStockRequest();
        request.setId(1);
        request.setQuantity(5);

        // 2. ejecutar test
        mockMvc
            .perform(
                put("/catalog/restore-stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isNoContent());
    }

    // Codigo 400 - campos invalidos (id y cantidad negativos)
    @Test
    public void testRestoreStockInvalidFields() throws Exception {
        // 1. preparar request prueba
        RestoreStockRequest request = new RestoreStockRequest();
        request.setId(-1);
        request.setQuantity(-5);

        // 2. ejecutar test
        mockMvc
            .perform(
                put("/catalog/restore-stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest());
    }

    // Codigo 404 - producto no existe
    @Test
    public void testRestoreStockProductNotFound() throws Exception {
        // 1. preparar request prueba
        RestoreStockRequest request = new RestoreStockRequest();
        request.setId(999);
        request.setQuantity(5);

        // 2. ejecutar test
        doThrow(new ProductDoesNotExistException())
            .when(service)
            .restoreStock(request);

        mockMvc
            .perform(
                put("/catalog/restore-stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isNotFound());
    }
}
