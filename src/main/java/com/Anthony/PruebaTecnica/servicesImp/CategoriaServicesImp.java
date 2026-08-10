package com.Anthony.PruebaTecnica.servicesImp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Anthony.PruebaTecnica.dto.CategoriaDTO;
import com.Anthony.PruebaTecnica.entities.Categoria;
import com.Anthony.PruebaTecnica.entities.Producto;
import com.Anthony.PruebaTecnica.repositories.CategoriaRepository;
import com.Anthony.PruebaTecnica.repositories.ProductoRepository;
import com.Anthony.PruebaTecnica.services.CategoriaServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServicesImp implements CategoriaServices {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;
    private final ProductoRepository productoRepository;

    @Override
    public List<CategoriaDTO> getAll() {
        return categoriaRepository.findAll().stream()
                .map(c -> modelMapper.map(c, CategoriaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO getById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    @Override
    public CategoriaDTO create(CategoriaDTO dto) {
        if (categoriaRepository.existsByNombre(dto.getNombre())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoría ya existe");
        }
        Categoria categoria = modelMapper.map(dto, Categoria.class);
        categoria.setId(null);
        Categoria guardada = categoriaRepository.save(categoria);
        return modelMapper.map(guardada, CategoriaDTO.class);
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        Categoria actualizada = categoriaRepository.save(categoria);
        return modelMapper.map(actualizada, CategoriaDTO.class);
    }

    @Override
    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        List<Producto> productosAsociados = productoRepository.findByCategoriaId(id);
        if (!productosAsociados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar: la categoría tiene " + productosAsociados.size()
                            + " producto(s) asociado(s)");
        }

        categoriaRepository.deleteById(id);
    }
}
