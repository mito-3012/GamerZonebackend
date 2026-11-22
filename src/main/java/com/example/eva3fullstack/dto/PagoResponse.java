package com.example.eva3fullstack.dto;

import com.example.eva3fullstack.entity.Pago;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagoResponse(Long idPago, Long idPedido, String metodoPago, BigDecimal monto,
                           String estado, String referenciaTransaccion, String moneda,
                           LocalDateTime fechaPago, String observacion) {
    public static PagoResponse from(Pago p) {
        return new PagoResponse(p.getIdPago(), p.getPedido().getIdPedido(), p.getMetodoPago(),
            p.getMonto(), p.getEstado(), p.getReferenciaTransaccion(), p.getMoneda(),
            p.getFechaPago(), p.getObservacion());
    }
}
