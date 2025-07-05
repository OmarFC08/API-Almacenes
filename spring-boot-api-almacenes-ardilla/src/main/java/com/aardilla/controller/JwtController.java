package com.aardilla.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aardilla.jwt.security.JwtUtil;
import com.aardilla.model.A_Usuario;
import com.aardilla.repository.AUsuarioRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class JwtController {

	@Autowired
    private final AUsuarioRepository AUsuarioRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

    JwtController(AUsuarioRepository AUsuarioRepository) {
        this.AUsuarioRepository = AUsuarioRepository;
    }
	
	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (AUsuarioRepository.findByUsuario(request.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        A_Usuario nuevo = new A_Usuario();
        nuevo.setUsuario(request.getUsuario());
        nuevo.setConstrasena(passwordEncoder.encode(request.getContrasena())); // Hash
        nuevo.setTipo("USUARIO"); // o "ADMINISTRADOR" según el caso

        @SuppressWarnings("unused")
		A_Usuario guardado = AUsuarioRepository.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
    }
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getContrasena())
            );
            System.out.println("Usuario: " + auth.getName());
            System.out.println("Roles: " + auth.getAuthorities());
            String token = jwtUtil.generateToken((UserDetails) auth.getPrincipal());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

	public static class AuthRequest {
        private String usuario;
        private String constrasena;

        public String getUsuario() { return usuario; }
        public void setUsuario(String usuario) { this.usuario = usuario; }
        public String getContrasena() { return constrasena; }
        public void setContrasena(String contrasena) { this.constrasena = contrasena; }
    }
	
}
