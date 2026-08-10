package com.Anthony.PruebaTecnica.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Anthony.PruebaTecnica.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
