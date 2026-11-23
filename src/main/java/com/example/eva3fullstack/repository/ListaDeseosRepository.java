package com.example.eva3fullstack.repository;

import com.example.eva3fullstack.entity.ListaDeseos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long> {
    List<ListaDeseos> findByUsuario_IdUsuario(Long usuarioId);
}