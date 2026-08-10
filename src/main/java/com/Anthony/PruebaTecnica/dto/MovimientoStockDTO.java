package com.Anthony.PruebaTecnica.dto;

import java.time.LocalDateTime;

import com.Anthony.PruebaTecnica.entities.TipoMovimiento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoStockDTO {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private TipoMovimiento tipo;
    private Integer cantidad;
    private String motivo;
    private String usuarioNombre;
    private LocalDateTime fechaMovimiento;
}
