package com.Anthony.PruebaTecnica.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String sku;
    private String nombre;
    private CategoriaDTO categoria;
    private BigDecimal precio;
    private Integer stockActual;
    private Integer stockMinimo;
    private String unidadMedida;
    private Boolean activo;
}
