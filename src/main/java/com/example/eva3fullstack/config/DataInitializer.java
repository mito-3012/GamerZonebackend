package com.example.eva3fullstack.config;

import com.example.eva3fullstack.entity.Usuario;
import com.example.eva3fullstack.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsuarios(UsuarioRepository usuarioRepository) {
        return args -> {
            if (!usuarioRepository.existsByEmail("gonzalo@duoc.cl")) {
                Usuario admin = new Usuario();
                admin.setRun("19011022K");
                admin.setNombre("Gonzalo");
                admin.setApellido("Gamer");
                admin.setEmail("gonzalo@duoc.cl");
                admin.setPassword("19011022K");
                admin.setTelefono("911111111");
                admin.setRegion("Región Metropolitana");
                admin.setComuna("Santiago");
                admin.setDireccion("Av. Siempre Viva 100");
                admin.setRol("Administrador");
                admin.setFechaRegistro(LocalDateTime.now());
                usuarioRepository.save(admin);
            }

            if (!usuarioRepository.existsByEmail("carlos@tiendagamer.cl")) {
                Usuario vendedor = new Usuario();
                vendedor.setRun("156667778");
                vendedor.setNombre("Carlos");
                vendedor.setApellido("Vendedor");
                vendedor.setEmail("carlos@tiendagamer.cl");
                vendedor.setPassword("156667778");
                vendedor.setTelefono("922222222");
                vendedor.setRegion("Región Metropolitana");
                vendedor.setComuna("Puente Alto");
                vendedor.setDireccion("Calle Gamer 200");
                vendedor.setRol("Vendedor");
                vendedor.setFechaRegistro(LocalDateTime.now());
                usuarioRepository.save(vendedor);
            }

            if (!usuarioRepository.existsByEmail("camila@gmail.com")) {
                Usuario cliente = new Usuario();
                cliente.setRun("201234567");
                cliente.setNombre("Camila");
                cliente.setApellido("Player");
                cliente.setEmail("camila@gmail.com");
                cliente.setPassword("201234567");
                cliente.setTelefono("933333333");
                cliente.setRegion("Valparaíso");
                cliente.setComuna("Viña del Mar");
                cliente.setDireccion("Av. Libertad 300");
                cliente.setRol("Cliente");
                cliente.setFechaRegistro(LocalDateTime.now());
                usuarioRepository.save(cliente);
            }
        };
    }
}