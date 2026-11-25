package com.example.eva3fullstack.service;

import com.example.eva3fullstack.dto.PagoRequest;
import com.example.eva3fullstack.entity.Pago;
import com.example.eva3fullstack.entity.Pedido;
import com.example.eva3fullstack.repository.PagoRepository;
import com.example.eva3fullstack.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PagoService {
    private final PagoRepository pagos;
    private final PedidoRepository pedidos;

    public PagoService(PagoRepository pagos, PedidoRepository pedidos) {
        this.pagos = pagos;
        this.pedidos = pedidos;
    }

    @Transactional(readOnly = true)
    public List<Pago> listar() { return pagos.findAll(); }

    @Transactional(readOnly = true)
    public Pago obtenerPorId(Long id) {
        return pagos.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<Pago> listarPorPedido(Long idPedido) {
        if (!pedidos.existsById(idPedido)) throw new RecursoNoEncontradoException("Pedido no encontrado: " + idPedido);
        return pagos.findByPedido_IdPedido(idPedido);
    }

    @Transactional(readOnly = true)
    public List<Pago> listarPorEstado(String estado) { return pagos.findByEstadoIgnoreCase(estado); }

    public Pago crear(PagoRequest request) {
        Pedido pedido = pedidos.findById(request.idPedido())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado: " + request.idPedido()));
        if (pedido.getTotal() == null || pedido.getTotal().compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El pedido no tiene un total válido");
        if (request.monto().compareTo(pedido.getTotal()) != 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto debe coincidir con el total del pedido");
        if (pagos.findByPedido_IdPedido(pedido.getIdPedido()).stream().anyMatch(p -> "APROBADO".equals(p.getEstado())))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El pedido ya tiene un pago aprobado");

        Pago pago = new Pago();
        pago.setPedido(pedido);
        if (pedido.getUsuario() != null) {
            pago.setUsuario(pedido.getUsuario());
            pago.setEmailUsuario(pedido.getUsuario().getEmail());
        } else if (pedido.getEmailUsuario() != null && !pedido.getEmailUsuario().isBlank()) {
            pago.setEmailUsuario(pedido.getEmailUsuario());
        }
        pago.setMetodoPago(request.metodoPago().trim().toUpperCase());
        pago.setMonto(request.monto());
        pago.setMoneda("CLP");
        pago.setEstado("PENDIENTE");
        pago.setReferenciaTransaccion("PC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // Integración demostrativa: no procesa cargos reales.
        pago.setEstado("APROBADO");
        pedido.setEstado("PAGADO");
        return pagos.save(pago);
    }

    public Pago actualizarEstado(Long id, String estado) {
        Pago pago = obtenerPorId(id);
        String nuevo = estado == null ? "" : estado.trim().toUpperCase();
        if (!List.of("PENDIENTE", "APROBADO", "RECHAZADO", "ANULADO").contains(nuevo))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado de pago no válido");
        pago.setEstado(nuevo);
        if ("APROBADO".equals(nuevo)) pago.getPedido().setEstado("PAGADO");
        else if ("RECHAZADO".equals(nuevo)) pago.getPedido().setEstado("PENDIENTE");
        return pagos.save(pago);
    }

    public void eliminar(Long id) { pagos.delete(obtenerPorId(id)); }
}