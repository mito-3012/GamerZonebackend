package com.example.eva3fullstack.controller;

import com.example.eva3fullstack.dto.NoticiaDTO;
import com.example.eva3fullstack.entity.Noticia;
import com.example.eva3fullstack.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/noticias")
@CrossOrigin(origins = "*")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @GetMapping
    public ResponseEntity<List<Noticia>> listar() {
        return ResponseEntity.ok(noticiaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Noticia> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(noticiaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Noticia> crear(@RequestBody NoticiaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noticiaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Noticia> actualizar(@PathVariable Long id, @RequestBody NoticiaDTO dto) {
        return ResponseEntity.ok(noticiaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        noticiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}