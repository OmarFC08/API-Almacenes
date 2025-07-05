package com.aardilla.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.aardilla.dto.EstadAlmacenContDTO;
import com.aardilla.dto.EstadisticasAlmacenDTO;
//import com.aardilla.dto.AProductoDTO;
import com.aardilla.model.A_Productos;
import com.aardilla.model.A_Tipo_Producto;
import com.aardilla.repository.AProductosRepository;
import com.aardilla.repository.ATipoProductoRepository;

@RestController
@RequestMapping("/api/productos")
public class AProductosController {

	@Autowired
	private AProductosRepository productosRepository;
	@Autowired
	private ATipoProductoRepository tipoProductoRepository;
	
	//OBTENER TODOS LOS PRODUCTOS
	@GetMapping
	public List<A_Productos> getProductos(){
		return productosRepository.findAll();
	}
	
	//CRAR UN PRODUCTO
	@PostMapping
	public ResponseEntity<?> createProducto(@RequestBody A_Productos producto) {
	    try {
	        if (producto.getTipoProducto() == null || producto.getTipoProducto().getId() == 0) {
	            return ResponseEntity.badRequest().body("El tipo de producto es obligatorio.");
	        }
	        A_Tipo_Producto tipoProducto = tipoProductoRepository.findById(producto.getTipoProducto().getId())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de producto no existe."));
	        producto.setTipoProducto(tipoProducto);
	        A_Productos saved = productosRepository.save(producto);
	        return ResponseEntity.ok(saved);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	//OBTENER PRODUCTOS POR ID
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductoById(@PathVariable Integer id){
		return productosRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}
	
	//MODIFICAR UN PRODUCTO POR ID
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProducto(@PathVariable Integer id, @RequestBody A_Productos productoActualizado){
		try {
	        return productosRepository.findById(id).map(productoExistente -> {
	            productoExistente.setNombre(productoActualizado.getNombre());
	            productoExistente.setPrecio(productoActualizado.getPrecio());
	            productoExistente.setStock(productoActualizado.getStock());

	            if (productoActualizado.getTipoProducto() != null && productoActualizado.getTipoProducto().getId() != 0) {
	                A_Tipo_Producto tipoProducto = tipoProductoRepository.findById(productoActualizado.getTipoProducto().getId())
	                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de producto no existe."));
	                productoExistente.setTipoProducto(tipoProducto);
	            }

	            A_Productos actualizado = productosRepository.save(productoExistente);
	            return ResponseEntity.ok(actualizado);
	        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}

	// ELIMINAR UN PRODUCTO
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProducto(@PathVariable Integer id) {
	    return productosRepository.findById(id).map(producto -> {
	        productosRepository.delete(producto);
	        return ResponseEntity.ok("Producto eliminado correctamente");
	    }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado"));
	}
	
	@GetMapping("/estadisticas")
	public ResponseEntity<?> getInventario(){
		try {
			//OBTENEMOS LOS PRODUCTOS DE LA BD
			List<A_Productos> productos = productosRepository.findAll();
			
			//OBTENEMOS DATOS DEL DTO
			List<EstadisticasAlmacenDTO> reporte = productos.stream().map(p -> {
				String tipoProd = p.getTipoProducto() != null ? p.getTipoProducto().getNombre() : "Sin Tipo";
				return new EstadisticasAlmacenDTO(
						p.getNombre(), 
						tipoProd, 
						p.getPrecio(), 
						p.getStock());
			}).toList();
			
			//CALCULAMOS VALOR TOTAL DEL INVENTARIO
			double totalInventario = reporte.stream().mapToDouble(EstadisticasAlmacenDTO::getValorTotal).sum();
			
			EstadAlmacenContDTO res = new EstadAlmacenContDTO(reporte, totalInventario);
			
			return ResponseEntity.ok(res);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "No se pudo obtener el inventario"));
		}
	}
		
}
