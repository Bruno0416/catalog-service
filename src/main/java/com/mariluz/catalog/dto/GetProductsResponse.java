package com.mariluz.catalog.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class GetProductsResponse {

    List<ProductResponse> products;
}
