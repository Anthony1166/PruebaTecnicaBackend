package com.Anthony.PruebaTecnica.services;

import java.util.List;

import com.Anthony.PruebaTecnica.dto.CategoriaDTO;

public interface CategoriaServices {
    List<CategoriaDTO> getAll();

    CategoriaDTO getById(Long id);

    CategoriaDTO create(CategoriaDTO dto);

    CategoriaDTO update(Long id, CategoriaDTO dto);

    void delete(Long id);
}
