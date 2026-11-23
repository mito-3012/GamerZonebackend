package com.example.eva3fullstack.repository;

import com.example.eva3fullstack.entity.ResenaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResenaProductoRepository extends JpaRepository<ResenaProducto, Long> {
    List<ResenaProducto> findByProductoId(Long productoId);
}