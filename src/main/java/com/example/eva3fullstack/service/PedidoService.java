package com.example.eva3fullstack.service;

import com.example.eva3fullstack.dto.PedidoCrearRequest;
import com.example.eva3fullstack.entity.DetallePedido;
import com.example.eva3fullstack.entity.Pedido;
import com.example.eva3fullstack.entity.Producto;
import com.example.eva3fullstack.entity.Usuario;
import com.example.eva3fullstack.repository.DetallePedidoRepository;
import com.example.eva3fullstack.repository.PedidoRepository;
import com.example.eva3fullstack.repository.ProductoRepository;
import com.example.eva3fullstack.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidos;
    private final UsuarioRepository usuarios;
    private final ProductoRepository productos;
    private final DetallePedidoRepository detalles;

    public PedidoService(
            PedidoRepository pedidos,
            UsuarioRepository usuarios,
            ProductoRepository productos,
            DetallePedidoRepository detalles) {
        this.pedidos = pedidos;
        this.usuarios = usuarios;
        this.productos = productos;
        this.detalles = detalles;
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidos.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        return pedidos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado con ID: " + id
                ));
    }

    @Transactional
    public Pedido crear(Pedido entidad) {
        if (entidad.getFechaPedido() == null) {
            entidad.setFechaPedido(LocalDateTime.now());
        }
        if (entidad.getEstado() == null || entidad.getEstado().isBlank()) {
            entidad.setEstado("PENDIENTE");
        }
        if (entidad.getUsuario() != null && entidad.getUsuario().getId() != null) {
            usuarios.findById(entidad.getUsuario().getId()).ifPresent(u -> {
                entidad.setUsuario(u);
                if (entidad.getEmailUsuario() == null || entidad.getEmailUsuario().isBlank()) {
                    entidad.setEmailUsuario(u.getEmail());
                }
            });
        }
        return pedidos.save(entidad);
    }

    @Transactional
    public Pedido actualizar(Long id, Pedido entidad) {
        Pedido existente = buscarPorId(id);

        if (entidad.getEstado() != null) {
            existente.setEstado(entidad.getEstado());
        }
        if (entidad.getDireccionDespacho() != null) {
            existente.setDireccionDespacho(entidad.getDireccionDespacho());
        }
        if (entidad.getSubtotal() != null) {
            existente.setSubtotal(entidad.getSubtotal());
        }
        if (entidad.getDescuento() != null) {
            existente.setDescuento(entidad.getDescuento());
        }
        if (entidad.getTotal() != null) {
            existente.setTotal(entidad.getTotal());
        }
        if (entidad.getCupon() != null) {
            existente.setCupon(entidad.getCupon());
        }

        return pedidos.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!pedidos.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se puede eliminar: Pedido no encontrado con ID: " + id
            );
        }
        pedidos.deleteById(id);
    }

    @Transactional
    public Pedido crearDesdeCarrito(PedidoCrearRequest request) {
        Usuario usuario = usuarios.findById(request.idUsuario())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        if (request.items() == null || request.items().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El carrito está vacío"
            );
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEmailUsuario(usuario.getEmail());
        pedido.setDireccionDespacho(request.direccionDespacho());
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setSubtotal(BigDecimal.ZERO);
        pedido.setDescuento(BigDecimal.ZERO);
        pedido.setTotal(BigDecimal.ZERO);
        pedido.setEstado("PENDIENTE");

        Pedido pedidoGuardado = pedidos.saveAndFlush(pedido);

        BigDecimal subtotal = BigDecimal.ZERO;

        for (PedidoCrearRequest.ItemPedido item : request.items()) {
            if (item.cantidad() == null || item.cantidad() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La cantidad debe ser mayor que cero"
                );
            }

            Producto producto = productos.findById(item.idProducto())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Producto no encontrado con ID: " + item.idProducto()
                    ));

            BigDecimal precio = producto.getPrecio();
            BigDecimal totalProducto = precio.multiply(BigDecimal.valueOf(item.cantidad()));
            subtotal = subtotal.add(totalProducto);

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedidoGuardado);
            detalle.setProducto(producto);
            detalle.setCantidad(item.cantidad());
            detalle.setPrecioUnitario(precio);
            detalle.setSubtotal(totalProducto);

            detalles.save(detalle);
        }

        pedidoGuardado.setSubtotal(subtotal);
        pedidoGuardado.setDescuento(BigDecimal.ZERO);
        pedidoGuardado.setTotal(subtotal);

        return pedidos.save(pedidoGuardado);
    }
}