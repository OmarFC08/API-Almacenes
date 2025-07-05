package com.aardilla.jwt.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aardilla.model.A_Usuario;

public class UsuarioDetails implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private A_Usuario usuario;

	public UsuarioDetails(A_Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return List.of(new SimpleGrantedAuthority("ROLE_"+usuario.getTipo()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return usuario.getConstrasena();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return usuario.getUsuario();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override public boolean isAccountNonLocked() { 
		return true; 
	}
    @Override public boolean isCredentialsNonExpired() {
    	return true;
    }
    @Override public boolean isEnabled() { 
    	return true;
    }
}
