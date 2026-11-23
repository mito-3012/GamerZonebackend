package com.example.eva3fullstack.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "noticias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 500)
    private String resumen;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String parrafoPrincipal;

    @Column(columnDefinition = "TEXT")
    private String parrafoSecundario;

    private String imagenUrl;

    // Links guardados en BD para mostrar en el frontend
    private String linkExterno;
    private String textoLink;

    private String autor;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion = LocalDateTime.now();
}