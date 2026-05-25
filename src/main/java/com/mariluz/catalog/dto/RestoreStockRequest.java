/*
    NOTA: Metodo para restaurar el stock de un producto, inverso a updateStock
*/
package com.mariluz.catalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RestoreStockRequest {

    @NotNull(message = "El id del producto es obligatorio")
    @Positive(message = "El id del producto debe ser mayor que 0")
    private Integer id;

    @NotNull(message = "La cantidad a restaurar es obligatoria")
    @Positive(message = "La cantidad a restaurar debe ser mayor que 0")
    private Integer quantity;
}
