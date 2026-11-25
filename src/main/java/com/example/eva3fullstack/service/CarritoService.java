package com.example.eva3fullstack.service;

import com.example.eva3fullstack.entity.Carrito;
import com.example.eva3fullstack.entity.Usuario;
import com.example.eva3fullstack.repository.CarritoRepository;
import com.example.eva3fullstack.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public CarritoService(CarritoRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Carrito> listarTodos() {
        return repository.findAll();
    }

    public Carrito buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Carrito no encontrado"));
    }

    public Carrito crear(Carrito entidad) {
        vincularUsuarioYEmail(entidad);
        return repository.save(entidad);
    }

    public Carrito actualizar(Long id, Carrito datos) {
        Carrito existente = buscarPorId(id);
        if (datos.getEstado() != null) {
            existente.setEstado(datos.getEstado());
        }
        if (datos.getUsuario() != null && datos.getUsuario().getId() != null) {
            existente.setUsuario(datos.getUsuario());
        }
        vincularUsuarioYEmail(existente);
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Carrito no encontrado");
        }
        repository.deleteById(id);
    }

    private void vincularUsuarioYEmail(Carrito carrito) {
        if (carrito.getUsuario() != null && carrito.getUsuario().getId() != null) {
            Usuario usuarioReal = usuarioRepository.findById(carrito.getUsuario().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            carrito.setUsuario(usuarioReal);
            if (carrito.getEmailUsuario() == null || carrito.getEmailUsuario().isBlank()) {
                carrito.setEmailUsuario(usuarioReal.getEmail());
            }
        }
    }
}