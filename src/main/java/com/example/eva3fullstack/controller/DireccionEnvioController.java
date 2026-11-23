package com.example.eva3fullstack.controller;

import com.example.eva3fullstack.dto.DireccionEnvioDTO;
import com.example.eva3fullstack.entity.DireccionEnvio;
import com.example.eva3fullstack.service.DireccionEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
@CrossOrigin(origins = "*")
public class DireccionEnvioController {

    @Autowired
    private DireccionEnvioService direccionService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DireccionEnvio>> porUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(direccionService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<DireccionEnvio> crear(@RequestBody DireccionEnvioDTO dto) {
        return ResponseEntity.ok(direccionService.guardar(dto));
    }
}