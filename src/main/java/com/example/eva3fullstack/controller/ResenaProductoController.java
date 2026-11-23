package com.example.eva3fullstack.controller;

import com.example.eva3fullstack.dto.ResenaProductoDTO;
import com.example.eva3fullstack.entity.ResenaProducto;
import com.example.eva3fullstack.service.ResenaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@CrossOrigin(origins = "*")
public class ResenaProductoController {

    @Autowired
    private ResenaProductoService resenaService;

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ResenaProducto>> porProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(resenaService.listarPorProducto(productoId));
    }

    @PostMapping
    public ResponseEntity<ResenaProducto> crear(@RequestBody ResenaProductoDTO dto) {
        return ResponseEntity.ok(resenaService.crear(dto));
    }
}