package com.example.eva3fullstack.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resenas_productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private Integer calificacion; // 1 a 5

    @Column(columnDefinition = "TEXT")
    private String comentario;

    private LocalDateTime fecha = LocalDateTime.now();
}