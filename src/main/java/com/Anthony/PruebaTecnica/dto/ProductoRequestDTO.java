package com.Anthony.PruebaTecnica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {
    private String sku;
    private String nombre;
    private Long categoriaId;
    private BigDecimal precio;
    private Integer stockMinimo;
    private String unidadMedida;
}