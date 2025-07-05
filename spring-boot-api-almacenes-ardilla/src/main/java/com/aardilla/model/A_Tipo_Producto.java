package com.aardilla.model;

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
@Table(name = "A_TIPO_PRODUCTO")
public class A_Tipo_Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_a_tipo_producto")
    @SequenceGenerator(name = "seq_a_tipo_producto", sequenceName = "SEQ_A_TIPO_PRODUCTO", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NOMBRE")
	private String nombre;
}
