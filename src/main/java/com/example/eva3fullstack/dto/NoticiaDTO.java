package com.example.eva3fullstack.dto;

import lombok.Data;

@Data
public class NoticiaDTO {
    private Long id;
    private String titulo;
    private String resumen;
    private String parrafoPrincipal;
    private String parrafoSecundario;
    private String imagenUrl;
    private String linkExterno;
    private String textoLink;
    private String autor;
}