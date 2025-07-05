package com.aardilla.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "A_USUARIO")
public class A_Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_a_usuario")
    @SequenceGenerator(name = "seq_a_usuario", sequenceName = "SEQ_A_USUARIO", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "USUARIO")
	private String usuario;
	@Column(name = "CONSTRASENA")
	@JsonIgnore
	private String constrasena;
	@Column(name = "tipo")
	@JsonIgnore
	private String tipo;
}
