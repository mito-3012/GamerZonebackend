package com.example.eva3fullstack.repository;

import com.example.eva3fullstack.entity.DireccionEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DireccionEnvioRepository extends JpaRepository<DireccionEnvio, Long> {
    List<DireccionEnvio> findByUsuarioId(Long usuarioId);
}