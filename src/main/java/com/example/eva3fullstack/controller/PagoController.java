package com.example.eva3fullstack.controller;

import com.example.eva3fullstack.dto.PagoRequest;
import com.example.eva3fullstack.dto.PagoResponse;
import com.example.eva3fullstack.entity.Pago;
import com.example.eva3fullstack.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Procesamiento y simulación de pagos")
public class PagoController {

    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos los pagos")
    public List<PagoResponse> listar() {
        return service.listar().stream().map(PagoResponse::from).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID")
    public PagoResponse obtener(@PathVariable Long id) {
        return PagoResponse.from(service.obtenerPorId(id));
    }

    @GetMapping("/pedido/{idPedido}")
    @Operation(summary = "Listar pagos asociados a un pedido")
    public List<PagoResponse> porPedido(@PathVariable Long idPedido) {
        return service.listarPorPedido(idPedido).stream().map(PagoResponse::from).toList();
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Filtrar pagos por estado")
    public List<PagoResponse> porEstado(@PathVariable String estado) {
        return service.listarPorEstado(estado).stream().map(PagoResponse::from).toList();
    }

    @PostMapping
    @Operation(summary = "Registrar un pago")
    public ResponseEntity<PagoResponse> crear(@Valid @RequestBody PagoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(PagoResponse.from(service.crear(request)));
    }

    @PostMapping("/simular")
    @Operation(summary = "Simular pago con tarjeta")
    public ResponseEntity<PagoResponse> simular(@Valid @RequestBody PagoRequest request) {
        Pago pagoCreado = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PagoResponse.from(pagoCreado));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de un pago")
    public PagoResponse cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return PagoResponse.from(service.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un registro de pago")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}