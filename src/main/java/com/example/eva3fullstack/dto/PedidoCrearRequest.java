package com.example.eva3fullstack.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PedidoCrearRequest(

        @NotNull
        Long idUsuario,

        @NotNull
        @Size(max = 255)
        String direccionDespacho,

        @NotEmpty
        List<@Valid ItemPedido> items

) {

    public record ItemPedido(

            @NotNull
            Long idProducto,

            @NotNull
            @Positive
            Integer cantidad

    ) {}

}