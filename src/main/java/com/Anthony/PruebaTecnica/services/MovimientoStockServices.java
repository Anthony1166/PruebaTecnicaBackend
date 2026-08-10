package com.Anthony.PruebaTecnica.services;

import java.util.List;

import com.Anthony.PruebaTecnica.dto.MovimientoStockDTO;
import com.Anthony.PruebaTecnica.dto.MovimientoStockRequestDTO;

public interface MovimientoStockServices {
    List<MovimientoStockDTO> getAll();

    List<MovimientoStockDTO> getByProductoId(Long productoId);

    MovimientoStockDTO registrar(MovimientoStockRequestDTO dto, String username);
}
