package com.example.eva3fullstack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "El RUN no puede estar vacío")
    private String run;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Debe ingresar un correo válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 4, max = 100, message = "La contraseña debe tener entre 4 y 100 caracteres")
    private String password;

    private String telefono;

    @NotBlank(message = "La región es obligatoria")
    private String region;

    @NotBlank(message = "La comuna es obligatoria")
    private String comuna;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    private String rol;
}