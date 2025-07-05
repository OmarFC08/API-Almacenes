package com.aardilla.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.aardilla.dto.DetalleVentaDTO;
import com.aardilla.dto.VentaDTO;
import com.aardilla.model.A_Detalle_Venta;
import com.aardilla.model.A_Productos;
import com.aardilla.model.A_Usuario;
import com.aardilla.model.A_Ventas;
import com.aardilla.repository.ADetalleVentaRepository;
import com.aardilla.repository.AProductosRepository;
import com.aardilla.repository.AUsuarioRepository;
import com.aardilla.repository.AVentasRepository;

@RestController
@RequestMapping("/api/ventas")
public class AVentasController {

	@Autowired
	private AVentasRepository ventasRepository;
	@Autowired
	private AUsuarioRepository usuarioRepository;
	@Autowired
	private AProductosRepository productosRepository;
	@Autowired
	private ADetalleVentaRepository detalleVentaRepository;
	
	// REGISTRAR UNA VENTA CON DETALLES
    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody VentaDTO dto) {
        try {
            A_Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

            A_Ventas venta = new A_Ventas();
            venta.setFecha(new Date());
            venta.setUsuario(usuario);
            venta.setFormaPago(dto.getFormaPago());

            double total = 0.0;
            List<A_Detalle_Venta> listaDetalles = new ArrayList<>();

            for (DetalleVentaDTO detalleDTO : dto.getDetalles()) {
                
            	//SE EVALUA SI EXISTE EL PRODUCTO A VENDER
            	A_Productos producto = productosRepository.findById(detalleDTO.getIdProducto())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado"));

            	//SE INGRESA EL PRODUCTO AL DETALLE VENTA
                A_Detalle_Venta detalle = new A_Detalle_Venta();
                detalle.setProducto(producto);
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());

                double subtotal = detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario();
                detalle.setSubtotal(subtotal);

                detalle.setVenta(venta); // importante para la relación
                listaDetalles.add(detalle);

                total += subtotal;
            }

            venta.setTotal(total);
            A_Ventas ventaGuardada = ventasRepository.save(venta);
            detalleVentaRepository.saveAll(listaDetalles);

            return ResponseEntity.status(HttpStatus.CREATED).body(ventaGuardada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // LISTAR TODAS LAS VENTAS
    @GetMapping
    public ResponseEntity<List<A_Ventas>> getAllVentas() {
        return ResponseEntity.ok(ventasRepository.findAll());
    }

    // OBTENER UNA VENTA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<A_Ventas> getVentaById(@PathVariable Integer id) {
        return ventasRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
