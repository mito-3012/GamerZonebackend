package com.example.eva3fullstack.service;

import com.example.eva3fullstack.dto.DireccionEnvioDTO;
import com.example.eva3fullstack.entity.DireccionEnvio;
import com.example.eva3fullstack.repository.DireccionEnvioRepository;
import com.example.eva3fullstack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DireccionEnvioService {

    @Autowired
    private DireccionEnvioRepository direccionRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;

    public List<DireccionEnvio> listarPorUsuario(Long usuarioId) {
        return direccionRepo.findByUsuarioId(usuarioId);
    }

    public DireccionEnvio guardar(DireccionEnvioDTO dto) {
        DireccionEnvio d = new DireccionEnvio();
        d.setUsuario(usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado")));
        d.setCalle(dto.getCalle());
        d.setNumero(dto.getNumero());
        d.setComuna(dto.getComuna());
        d.setRegion(dto.getRegion());
        d.setCodigoPostal(dto.getCodigoPostal());
        d.setPrincipal(dto.getPrincipal() != null ? dto.getPrincipal() : false);
        return direccionRepo.save(d);
    }
}