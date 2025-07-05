package com.aardilla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aardilla.jwt.security.JwtFiltro;
import com.aardilla.jwt.security.UsuarioDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfg {
	
	@Autowired
    private JwtFiltro jwtFiltro;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    	http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/login", "/auth/register").permitAll()
            .requestMatchers("/api/**").hasAnyRole("ADMIN", "USUARIO")
            .anyRequest().authenticated()
        )
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFiltro, UsernamePasswordAuthenticationFilter.class);

    	return http.build();
    }
    
    @SuppressWarnings("removal")
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UsuarioDetailsService uds) 
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(uds)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	/*
	//PERMITE HACER LAS PRUEBAS SIN AUTENTICARSE
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()); // PERMITE HACER TODAS LAS PETICIONES
        return http.build();
    }
    */
}
