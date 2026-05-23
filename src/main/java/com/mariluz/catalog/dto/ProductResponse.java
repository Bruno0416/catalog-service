package com.mariluz.catalog.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductResponse {

    private Integer id;

    private String name;

    private Integer price;

    private Integer quantity;
}
