package com.aardilla.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aardilla.model.A_Usuario;

public interface AUsuarioRepository extends JpaRepository<A_Usuario, Integer>{
	Optional<A_Usuario> findByUsuario(String usuario);
}
