package com.Anthony.PruebaTecnica.servicesImp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Anthony.PruebaTecnica.dto.ProductoDTO;
import com.Anthony.PruebaTecnica.dto.ProductoRequestDTO;
import com.Anthony.PruebaTecnica.entities.Categoria;
import com.Anthony.PruebaTecnica.entities.Producto;
import com.Anthony.PruebaTecnica.repositories.CategoriaRepository;
import com.Anthony.PruebaTecnica.repositories.ProductoRepository;
import com.Anthony.PruebaTecnica.services.ProductoServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServicesImp implements ProductoServices {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductoDTO> getAll() {
        return productoRepository.findByActivoTrue().stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO getById(Long id) {
        Producto producto = findProductoOrThrow(id);
        return modelMapper.map(producto, ProductoDTO.class);
    }

    @Override
    public ProductoDTO create(ProductoRequestDTO dto) {
        if (productoRepository.existsBySku(dto.getSku())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El SKU ya existe");
        }
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setSku(dto.getSku());
        producto.setNombre(dto.getNombre());
        producto.setCategoria(categoria);
        producto.setPrecio(dto.getPrecio());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setUnidadMedida(dto.getUnidadMedida());
        producto.setStockActual(0);
        producto.setActivo(true);

        Producto guardado = productoRepository.save(producto);
        return modelMapper.map(guardado, ProductoDTO.class);
    }

    @Override
    public ProductoDTO update(Long id, ProductoRequestDTO dto) {
        Producto producto = findProductoOrThrow(id);
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        producto.setNombre(dto.getNombre());
        producto.setCategoria(categoria);
        producto.setPrecio(dto.getPrecio());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setUnidadMedida(dto.getUnidadMedida());

        Producto actualizado = productoRepository.save(producto);
        return modelMapper.map(actualizado, ProductoDTO.class);
    }

    @Override
    public void delete(Long id) {
        Producto producto = findProductoOrThrow(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Override
    public List<ProductoDTO> getStockBajo() {
        return productoRepository.findProductosConStockBajo().stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    private Producto findProductoOrThrow(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    @Override
    public void reactivar(Long id) {
        Producto producto = findProductoOrThrow(id);
        producto.setActivo(true);
        productoRepository.save(producto);
    }
}
