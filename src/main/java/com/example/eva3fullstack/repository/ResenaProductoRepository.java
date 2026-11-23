package com.example.eva3fullstack.repository;

import com.example.eva3fullstack.entity.ResenaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResenaProductoRepository extends JpaRepository<ResenaProducto, Long> {
    List<ResenaProducto> findByProducto_IdProducto(Long productoId);
}