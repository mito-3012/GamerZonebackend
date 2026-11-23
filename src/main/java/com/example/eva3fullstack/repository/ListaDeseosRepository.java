package com.example.eva3fullstack.repository;

import com.example.eva3fullstack.entity.ListaDeseos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long> {
    List<ListaDeseos> findByUsuarioId(Long usuarioId);
}