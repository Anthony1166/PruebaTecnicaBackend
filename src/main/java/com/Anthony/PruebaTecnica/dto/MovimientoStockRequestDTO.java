package com.Anthony.PruebaTecnica.dto;

import com.Anthony.PruebaTecnica.entities.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoStockRequestDTO {
    private Long productoId;
    private TipoMovimiento tipo;
    private Integer cantidad;
    private String motivo;
}