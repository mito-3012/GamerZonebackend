package com.example.eva3fullstack.controller;

import com.example.eva3fullstack.entity.Categoria;
import com.example.eva3fullstack.entity.Producto;
import com.example.eva3fullstack.repository.CategoriaRepository;
import com.example.eva3fullstack.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones CRUD del catálogo de productos")
public class ProductoController {

    private final ProductoService service;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoService service, CategoriaRepository categoriaRepository) {
        this.service = service;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos del catálogo")
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por su ID")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto (Solo Administrador o Vendedor)")
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> payload) {
        try {
            Producto entidad = mapearProductoDesdePayload(payload, new Producto());
            Producto nuevo = service.crear(entidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "No se pudo crear el producto: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente (Solo Administrador o Vendedor)")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {
        try {
            Producto existente = service.buscarPorId(id);
            Producto actualizadoData = mapearProductoDesdePayload(payload, existente);
            return ResponseEntity.ok(service.actualizar(id, actualizadoData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "No se pudo actualizar el producto: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto (Solo Administrador)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private Producto mapearProductoDesdePayload(Map<String, Object> payload, Producto producto) {
        if (payload.get("codigo") != null) {
            producto.setCodigo(payload.get("codigo").toString());
        }
        if (payload.get("nombre") != null) {
            producto.setNombre(payload.get("nombre").toString());
        }
        if (payload.get("descripcion") != null) {
            producto.setDescripcion(payload.get("descripcion").toString());
        }
        if (payload.get("precio") != null) {
            producto.setPrecio(new BigDecimal(payload.get("precio").toString()));
        }
        if (payload.get("stock") != null) {
            producto.setStock(Integer.parseInt(payload.get("stock").toString()));
        }
        if (payload.get("stockCritico") != null) {
            producto.setStockCritico(Integer.parseInt(payload.get("stockCritico").toString()));
        }
        if (payload.get("imagen") != null) {
            producto.setImagen(payload.get("imagen").toString());
        }

        // Resolución de categoría (por objeto con idCategoria o por nombre)
        String nombreCat = "General";
        Long idCat = null;

        if (payload.get("categoria") instanceof Map<?, ?> catMap) {
            if (catMap.get("idCategoria") != null) {
                idCat = Long.valueOf(catMap.get("idCategoria").toString());
            } else if (catMap.get("id") != null) {
                idCat = Long.valueOf(catMap.get("id").toString());
            }
            if (catMap.get("nombre") != null) {
                nombreCat = catMap.get("nombre").toString();
            }
        } else if (payload.get("categoriaNombre") != null && !payload.get("categoriaNombre").toString().isBlank()) {
            nombreCat = payload.get("categoriaNombre").toString();
        } else if (payload.get("categoria") instanceof String strCat && !strCat.isBlank()) {
            nombreCat = strCat;
        }

        Categoria categoriaAsignada = null;
        if (idCat != null) {
            categoriaAsignada = categoriaRepository.findById(idCat).orElse(null);
        }

        if (categoriaAsignada == null) {
            final String nombreFinal = nombreCat;
            categoriaAsignada = categoriaRepository.findAll().stream()
                    .filter(c -> c.getNombre() != null && c.getNombre().equalsIgnoreCase(nombreFinal))
                    .findFirst()
                    .orElseGet(() -> {
                        Categoria nuevaCat = new Categoria();
                        nuevaCat.setNombre(nombreFinal);
                        return categoriaRepository.save(nuevaCat);
                    });
        }

        producto.setCategoria(categoriaAsignada);
        return producto;
    }
}