package com.example.eva3fullstack.service;

import com.example.eva3fullstack.dto.ResenaProductoDTO;
import com.example.eva3fullstack.entity.ResenaProducto;
import com.example.eva3fullstack.repository.ProductoRepository;
import com.example.eva3fullstack.repository.ResenaProductoRepository;
import com.example.eva3fullstack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResenaProductoService {

    @Autowired
    private ResenaProductoRepository resenaRepo;
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;

    public List<ResenaProducto> listarPorProducto(Long productoId) {
        return resenaRepo.findByProducto_IdProducto(productoId);
    }

    public ResenaProducto crear(ResenaProductoDTO dto) {
        ResenaProducto r = new ResenaProducto();
        r.setProducto(productoRepo.findById(dto.getProductoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado")));
        r.setUsuario(usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado")));
        r.setCalificacion(dto.getCalificacion());
        r.setComentario(dto.getComentario());
        return resenaRepo.save(r);
    }
}