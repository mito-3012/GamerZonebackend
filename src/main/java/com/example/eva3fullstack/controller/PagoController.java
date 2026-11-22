package com.example.eva3fullstack.controller;

import com.example.eva3fullstack.dto.PagoRequest;
import com.example.eva3fullstack.dto.PagoResponse;
import com.example.eva3fullstack.entity.Pago;
import com.example.eva3fullstack.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "${FRONTEND_URL:http://localhost:5173}")
public class PagoController {
    private final PagoService service;
    public PagoController(PagoService service) { this.service = service; }

    @GetMapping public List<PagoResponse> listar() { return service.listar().stream().map(PagoResponse::from).toList(); }
    @GetMapping("/{id}") public PagoResponse obtener(@PathVariable Long id) { return PagoResponse.from(service.obtenerPorId(id)); }
    @GetMapping("/pedido/{idPedido}") public List<PagoResponse> porPedido(@PathVariable Long idPedido) { return service.listarPorPedido(idPedido).stream().map(PagoResponse::from).toList(); }
    @GetMapping("/estado/{estado}") public List<PagoResponse> porEstado(@PathVariable String estado) { return service.listarPorEstado(estado).stream().map(PagoResponse::from).toList(); }
    @PostMapping public ResponseEntity<PagoResponse> crear(@Valid @RequestBody PagoRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(PagoResponse.from(service.crear(request))); }
    @PatchMapping("/{id}/estado") public PagoResponse cambiarEstado(@PathVariable Long id, @RequestParam String estado) { return PagoResponse.from(service.actualizarEstado(id, estado)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminar(id); return ResponseEntity.noContent().build(); }
}
