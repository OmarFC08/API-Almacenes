package com.aardilla.jwt.security;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final String SECRET = "clave-super-secreta-clave-super-secreta"; 
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.claim("roles", userDetails.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10)) // 10 horas
				.signWith(SECRET_KEY, SignatureAlgorithm.HS256)//FIRMAR TOKEN
				.compact();
	}
	
	public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
	
	public boolean validateToken(String token, UserDetails userDetails) {
		return extractUsername(token).equals(userDetails.getUsername());
	}
}
