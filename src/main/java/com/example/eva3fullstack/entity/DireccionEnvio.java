package com.example.eva3fullstack.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "direcciones_envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String calle;
    private String numero;
    private String comuna;
    private String region;
    private String codigoPostal;
    private Boolean principal = false;
}