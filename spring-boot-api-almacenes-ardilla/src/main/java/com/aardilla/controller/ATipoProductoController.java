package com.aardilla.controller;

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

import com.aardilla.model.A_Tipo_Producto;
import com.aardilla.repository.ATipoProductoRepository;

@RestController
@RequestMapping("/api/tipo-producto")
public class ATipoProductoController {

	@Autowired
    private ATipoProductoRepository tipoProductoRepository;

    @GetMapping
    public List<A_Tipo_Producto> getAllTipos() {
        return tipoProductoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<A_Tipo_Producto> createTipo(@RequestBody A_Tipo_Producto tipoProducto) {
        A_Tipo_Producto saved = tipoProductoRepository.save(tipoProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<A_Tipo_Producto> getTipoById(@PathVariable Integer id) {
        return tipoProductoRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
