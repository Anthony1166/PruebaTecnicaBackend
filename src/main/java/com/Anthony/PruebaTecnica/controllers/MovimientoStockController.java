package com.Anthony.PruebaTecnica.controllers;

import com.Anthony.PruebaTecnica.dto.MovimientoStockDTO;
import com.Anthony.PruebaTecnica.dto.MovimientoStockRequestDTO;
import com.Anthony.PruebaTecnica.services.MovimientoStockServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoStockController {
    private final MovimientoStockServices movimientoStockService;

    @GetMapping
    public ResponseEntity<List<MovimientoStockDTO>> getAll() {
        return ResponseEntity.ok(movimientoStockService.getAll());
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<MovimientoStockDTO>> getByProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(movimientoStockService.getByProductoId(productoId));
    }

    @PostMapping
    public ResponseEntity<MovimientoStockDTO> registrar(
            @RequestBody MovimientoStockRequestDTO dto,
            Authentication authentication) {

        String username = authentication.getName();
        MovimientoStockDTO creado = movimientoStockService.registrar(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}
