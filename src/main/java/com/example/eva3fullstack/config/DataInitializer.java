package com.example.eva3fullstack.config;

import com.example.eva3fullstack.entity.Usuario;
import com.example.eva3fullstack.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Administrador
            usuarioRepository.findByEmail("gonzalo@duoc.cl").ifPresentOrElse(
                    existente -> {
                        if (!existente.getPassword().startsWith("$2a$")) {
                            existente.setPassword(passwordEncoder.encode("19011022K"));
                            usuarioRepository.save(existente);
                        }
                    },
                    () -> {
                        Usuario admin = new Usuario();
                        admin.setRun("19011022K");
                        admin.setNombre("Gonzalo");
                        admin.setApellido("Gamer");
                        admin.setEmail("gonzalo@duoc.cl");
                        admin.setPassword(passwordEncoder.encode("19011022K"));
                        admin.setTelefono("911111111");
                        admin.setRegion("Región Metropolitana");
                        admin.setComuna("Santiago");
                        admin.setDireccion("Av. Siempre Viva 100");
                        admin.setRol("Administrador");
                        admin.setFechaRegistro(LocalDateTime.now());
                        usuarioRepository.save(admin);
                    }
            );

            // 2. Vendedor
            usuarioRepository.findByEmail("carlos@tiendagamer.cl").ifPresentOrElse(
                    existente -> {
                        if (!existente.getPassword().startsWith("$2a$")) {
                            existente.setPassword(passwordEncoder.encode("156667778"));
                            usuarioRepository.save(existente);
                        }
                    },
                    () -> {
                        Usuario vendedor = new Usuario();
                        vendedor.setRun("156667778");
                        vendedor.setNombre("Carlos");
                        vendedor.setApellido("Vendedor");
                        vendedor.setEmail("carlos@tiendagamer.cl");
                        vendedor.setPassword(passwordEncoder.encode("156667778"));
                        vendedor.setTelefono("922222222");
                        vendedor.setRegion("Región Metropolitana");
                        vendedor.setComuna("Puente Alto");
                        vendedor.setDireccion("Calle Gamer 200");
                        vendedor.setRol("Vendedor");
                        vendedor.setFechaRegistro(LocalDateTime.now());
                        usuarioRepository.save(vendedor);
                    }
            );

            // 3. Cliente
            usuarioRepository.findByEmail("camila@gmail.com").ifPresentOrElse(
                    existente -> {
                        if (!existente.getPassword().startsWith("$2a$")) {
                            existente.setPassword(passwordEncoder.encode("201234567"));
                            usuarioRepository.save(existente);
                        }
                    },
                    () -> {
                        Usuario cliente = new Usuario();
                        cliente.setRun("201234567");
                        cliente.setNombre("Camila");
                        cliente.setApellido("Player");
                        cliente.setEmail("camila@gmail.com");
                        cliente.setPassword(passwordEncoder.encode("201234567"));
                        cliente.setTelefono("933333333");
                        cliente.setRegion("Valparaíso");
                        cliente.setComuna("Viña del Mar");
                        cliente.setDireccion("Av. Libertad 300");
                        cliente.setRol("Cliente");
                        cliente.setFechaRegistro(LocalDateTime.now());
                        usuarioRepository.save(cliente);
                    }
            );
        };
    }
}