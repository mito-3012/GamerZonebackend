package com.example.eva3fullstack.service;

import com.example.eva3fullstack.dto.UsuarioDTO;
import com.example.eva3fullstack.entity.Usuario;
import com.example.eva3fullstack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
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
        // Encriptación segura con BCrypt
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setTelefono(dto.getTelefono());
        u.setRegion(dto.getRegion());
        u.setComuna(dto.getComuna());
        u.setDireccion(dto.getDireccion());
        u.setRol(dto.getRol() != null && !dto.getRol().isBlank() ? dto.getRol() : "Cliente");

        return usuarioRepository.save(u);
    }

    public Usuario actualizarUsuario(Long id, UsuarioDTO dto) {
        Usuario existente = obtenerPorId(id);

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setTelefono(dto.getTelefono());
        existente.setRegion(dto.getRegion());
        existente.setComuna(dto.getComuna());
        existente.setDireccion(dto.getDireccion());

        if (dto.getRol() != null && !dto.getRol().isBlank()) {
            existente.setRol(dto.getRol());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return usuarioRepository.save(existente);
    }

    public void eliminarUsuario(Long id) {
        Usuario existente = obtenerPorId(id);
        usuarioRepository.delete(existente);
    }

    public Usuario autenticar(String email, String password) {
        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean coincide = passwordEncoder.matches(password, u.getPassword())
                || u.getPassword().equals(password);

        if (!coincide) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return u;
    }
}