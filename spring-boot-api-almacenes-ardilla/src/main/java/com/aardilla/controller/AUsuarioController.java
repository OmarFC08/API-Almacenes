package com.aardilla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aardilla.model.A_Usuario;
import com.aardilla.repository.AUsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class AUsuarioController {

	@Autowired
	private AUsuarioRepository usuarioRepository;
	
	@GetMapping
	public List<A_Usuario> getAllUsuarios(){
		return usuarioRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<A_Usuario> createUsuario(@RequestBody A_Usuario usuario){
		A_Usuario saved = usuarioRepository.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<A_Usuario> getUsuarioById(@PathVariable Integer id) {
		return usuarioRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	//ELIMINAR USUARIO POR ID
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Integer id){
		return usuarioRepository.findById(id).map(usuario -> {
			usuarioRepository.delete(usuario);
			return ResponseEntity.ok("Usuario eliminado correctamente");
		}).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
	}
}
