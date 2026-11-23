package com.example.eva3fullstack.repository;

import com.example.eva3fullstack.entity.DireccionEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DireccionEnvioRepository extends JpaRepository<DireccionEnvio, Long> {
    List<DireccionEnvio> findByUsuario_IdUsuario(Long usuarioId);
}