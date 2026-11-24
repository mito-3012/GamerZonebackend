package com.example.eva3fullstack.service;

import com.example.eva3fullstack.dto.ListaDeseosDTO;
import com.example.eva3fullstack.entity.ListaDeseos;
import com.example.eva3fullstack.repository.ListaDeseosRepository;
import com.example.eva3fullstack.repository.ProductoRepository;
import com.example.eva3fullstack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListaDeseosService {

    @Autowired
    private ListaDeseosRepository listaRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private ProductoRepository productoRepo;

    public List<ListaDeseos> listarPorUsuario(Long usuarioId) {
        return listaRepo.findByUsuarioId(usuarioId);
    }

    public ListaDeseos agregar(ListaDeseosDTO dto) {
        ListaDeseos item = new ListaDeseos();
        item.setUsuario(usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado")));
        item.setProducto(productoRepo.findById(dto.getProductoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado")));
        return listaRepo.save(item);
    }

    public void eliminar(Long id) {
        listaRepo.deleteById(id);
    }
}