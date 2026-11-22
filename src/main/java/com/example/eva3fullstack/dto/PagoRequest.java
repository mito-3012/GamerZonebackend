package com.example.eva3fullstack.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PagoRequest(

        @NotNull
        Long idPedido,

        @NotBlank
        @Size(max = 30)
        String metodoPago,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal monto

) {}