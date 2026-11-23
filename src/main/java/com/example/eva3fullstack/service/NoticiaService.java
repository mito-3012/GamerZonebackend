package com.example.eva3fullstack.service;

import com.example.eva3fullstack.dto.NoticiaDTO;
import com.example.eva3fullstack.entity.Noticia;
import com.example.eva3fullstack.repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    public List<Noticia> listarTodas() {
        return noticiaRepository.findAllByOrderByFechaPublicacionDesc();
    }

    public Noticia obtenerPorId(Long id) {
        return noticiaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Noticia no encontrada con id: " + id));
    }

    public Noticia crear(NoticiaDTO dto) {
        Noticia n = new Noticia();
        n.setTitulo(dto.getTitulo());
        n.setResumen(dto.getResumen());
        n.setParrafoPrincipal(dto.getParrafoPrincipal());
        n.setParrafoSecundario(dto.getParrafoSecundario());
        n.setImagenUrl(dto.getImagenUrl());
        n.setLinkExterno(dto.getLinkExterno());
        n.setTextoLink(dto.getTextoLink() != null ? dto.getTextoLink() : "Leer fuente original");
        n.setAutor(dto.getAutor() != null ? dto.getAutor() : "Equipo GamerZone");
        return noticiaRepository.save(n);
    }

    public Noticia actualizar(Long id, NoticiaDTO dto) {
        Noticia n = obtenerPorId(id);
        n.setTitulo(dto.getTitulo());
        n.setResumen(dto.getResumen());
        n.setParrafoPrincipal(dto.getParrafoPrincipal());
        n.setParrafoSecundario(dto.getParrafoSecundario());
        n.setImagenUrl(dto.getImagenUrl());
        n.setLinkExterno(dto.getLinkExterno());
        n.setTextoLink(dto.getTextoLink());
        n.setAutor(dto.getAutor());
        return noticiaRepository.save(n);
    }

    public void eliminar(Long id) {
        noticiaRepository.deleteById(id);
    }
}