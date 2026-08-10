package com.Anthony.PruebaTecnica.servicesImp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Anthony.PruebaTecnica.dto.MovimientoStockDTO;
import com.Anthony.PruebaTecnica.dto.MovimientoStockRequestDTO;
import com.Anthony.PruebaTecnica.entities.MovimientoStock;
import com.Anthony.PruebaTecnica.entities.Producto;
import com.Anthony.PruebaTecnica.entities.TipoMovimiento;
import com.Anthony.PruebaTecnica.repositories.MovimientoStockRepository;
import com.Anthony.PruebaTecnica.repositories.ProductoRepository;
import com.Anthony.PruebaTecnica.security.entities.User;
import com.Anthony.PruebaTecnica.security.entities.repositories.UserRepository;
import com.Anthony.PruebaTecnica.services.MovimientoStockServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoStockServicesImp implements MovimientoStockServices {
    private final MovimientoStockRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MovimientoStockDTO> getAll() {
        return movimientoRepository.findAllByOrderByFechaMovimientoDesc().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoStockDTO> getByProductoId(Long productoId) {
        return movimientoRepository.findByProductoIdOrderByFechaMovimientoDesc(productoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoStockDTO registrar(MovimientoStockRequestDTO dto, String username) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor a cero");
        }

        if (dto.getTipo() == TipoMovimiento.ENTRADA) {
            producto.setStockActual(producto.getStockActual() + dto.getCantidad());
        } else if (dto.getTipo() == TipoMovimiento.SALIDA) {
            if (producto.getStockActual() < dto.getCantidad()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Stock insuficiente. Stock actual: " + producto.getStockActual());
            }
            producto.setStockActual(producto.getStockActual() - dto.getCantidad());
        }

        productoRepository.save(producto);

        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setProducto(producto);
        movimiento.setTipo(dto.getTipo());
        movimiento.setCantidad(dto.getCantidad());
        movimiento.setMotivo(dto.getMotivo());
        movimiento.setUsuario(usuario);

        MovimientoStock guardado = movimientoRepository.save(movimiento);
        return toDTO(guardado);
    }

    private MovimientoStockDTO toDTO(MovimientoStock m) {
        MovimientoStockDTO dto = new MovimientoStockDTO();
        dto.setId(m.getId());
        dto.setProductoId(m.getProducto().getId());
        dto.setProductoNombre(m.getProducto().getNombre());
        dto.setTipo(m.getTipo());
        dto.setCantidad(m.getCantidad());
        dto.setMotivo(m.getMotivo());
        dto.setUsuarioNombre(m.getUsuario().getUsername());
        dto.setFechaMovimiento(m.getFechaMovimiento());
        return dto;
    }
}
