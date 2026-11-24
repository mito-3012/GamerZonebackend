package com.example.eva3fullstack.service;

import com.example.eva3fullstack.dto.UsuarioDTO;
import com.example.eva3fullstack.entity.Usuario;
import com.example.eva3fullstack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario registrarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        if (usuarioRepository.existsByRun(dto.getRun())) {
            throw new RuntimeException("El RUN ya está registrado");
        }

        Usuario u = new Usuario();
        u.setRun(dto.getRun());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setTelefono(dto.getTelefono());
        u.setRegion(dto.getRegion());
        u.setComuna(dto.getComuna());
        u.setDireccion(dto.getDireccion());
        u.setRol(dto.getRol() != null && !dto.getRol().isBlank() ? dto.getRol() : "Cliente");

        return usuarioRepository.save(u);
    }

    public Usuario autenticar(String email, String password) {
        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!u.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return u;
    }
}