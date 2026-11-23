package com.example.eva3fullstack.dto;

import lombok.Data;

@Data
public class DireccionEnvioDTO {
    private Long usuarioId;
    private String calle;
    private String numero;
    private String comuna;
    private String region;
    private String codigoPostal;
    private Boolean principal;
}