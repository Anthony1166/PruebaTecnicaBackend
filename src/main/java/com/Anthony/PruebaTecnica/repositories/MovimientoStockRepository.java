package com.Anthony.PruebaTecnica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Anthony.PruebaTecnica.entities.MovimientoStock;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Long> {

    List<MovimientoStock> findByProductoIdOrderByFechaMovimientoDesc(Long productoId);

    List<MovimientoStock> findAllByOrderByFechaMovimientoDesc();
}
