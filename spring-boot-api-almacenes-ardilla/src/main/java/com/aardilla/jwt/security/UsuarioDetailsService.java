package com.aardilla.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aardilla.model.A_Usuario;
import com.aardilla.repository.AUsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService{

	@Autowired
	private AUsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		A_Usuario usuario = usuarioRepository.findByUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
		return User.builder()
	            .username(usuario.getUsuario())
	            .password(usuario.getConstrasena())
	            .roles(usuario.getTipo()) // "ADMINISTRADOR" o "USUARIO"
	            .build();
	}
}
