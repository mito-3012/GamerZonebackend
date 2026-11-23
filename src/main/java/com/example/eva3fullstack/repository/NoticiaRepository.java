package com.example.eva3fullstack.repository;

import com.example.eva3fullstack.entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    List<Noticia> findAllByOrderByFechaPublicacionDesc();
}