package com.example.eva3fullstack.dto;

import lombok.Data;

@Data
public class ResenaProductoDTO {
    private Long productoId;
    private Long usuarioId;
    private Integer calificacion;
    private String comentario;
}