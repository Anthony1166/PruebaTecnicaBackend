package com.Anthony.PruebaTecnica.services;

import java.util.List;

import com.Anthony.PruebaTecnica.dto.ProductoDTO;
import com.Anthony.PruebaTecnica.dto.ProductoRequestDTO;

public interface ProductoServices {
    List<ProductoDTO> getAll();

    ProductoDTO getById(Long id);

    ProductoDTO create(ProductoRequestDTO dto);

    ProductoDTO update(Long id, ProductoRequestDTO dto);

    void delete(Long id);

    List<ProductoDTO> getStockBajo();

    void reactivar(Long id);
}
