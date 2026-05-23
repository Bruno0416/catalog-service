package com.mariluz.catalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateStockRequest {

    @NotNull(message = "El id del producto es obligatorio")
    @Positive(message = "El id del producto debe ser mayor que 0")
    private Integer id;

    // Representa la cantidad vendida a descontar del stock, por eso es @Positive (> 0)
    @NotNull(message = "La cantidad a descontar es obligatoria")
    @Positive(message = "La cantidad a descontar debe ser mayor que 0")
    private Integer quantity;
}
