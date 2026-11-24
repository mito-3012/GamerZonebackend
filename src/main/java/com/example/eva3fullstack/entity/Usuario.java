package com.example.eva3fullstack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUN es obligatorio")
    @Column(nullable = false, unique = true)
    private String run;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, max = 100, message = "La contraseña debe tener entre 4 y 100 caracteres")
    @Column(nullable = false)
    private String password;

    private String telefono;

    @NotBlank(message = "La región es obligatoria")
    @Column(nullable = false)
    private String region;

    @NotBlank(message = "La comuna es obligatoria")
    @Column(nullable = false)
    private String comuna;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String rol = "Cliente";

    @NotNull(message = "La fecha de registro no puede ser nula")
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}