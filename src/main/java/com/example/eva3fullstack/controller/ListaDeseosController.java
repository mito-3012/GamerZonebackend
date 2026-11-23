package com.example.eva3fullstack.controller;

import com.example.eva3fullstack.dto.ListaDeseosDTO;
import com.example.eva3fullstack.entity.ListaDeseos;
import com.example.eva3fullstack.service.ListaDeseosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/deseos")
@CrossOrigin(origins = "*")
public class ListaDeseosController {

    @Autowired
    private ListaDeseosService listaDeseosService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ListaDeseos>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(listaDeseosService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<ListaDeseos> agregar(@RequestBody ListaDeseosDTO dto) {
        return ResponseEntity.ok(listaDeseosService.agregar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        listaDeseosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}